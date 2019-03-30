package com.seaso.seaso.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class IdGenerationServer {

    private IdGenerationThread generatorThread;

    private Scheduler scheduler;

    public IdGenerationServer(IdGeneratable idGenerator, int cacheSize) {
        BlockingQueue<Long> idPool = new ArrayBlockingQueue<>(cacheSize);
        this.generatorThread = new IdGenerationThread(idGenerator, "generator", idPool);
        this.scheduler = new Scheduler(idPool, "scheduler");
    }

    public void start() {
        generatorThread.start();
        scheduler.start();
    }

    public List<Long> generateIds(int size) {
        try {
            return scheduler.generateIds(size);
        } catch (InterruptedException e) {
            throw new RuntimeException("Fetching Id is interrupted.");
        }
    }

    public void stop() {
        generatorThread.interrupt();
        scheduler.interrupt();
    }

    private class IdGenerationThread extends Thread {

        private final IdGeneratable idGenerator;

        private final BlockingQueue<Long> idPool;

        IdGenerationThread(IdGeneratable idGenerator, String name, BlockingQueue<Long> idPool) {
            this.idGenerator = idGenerator;
            this.setName(name);
            this.idPool = idPool;
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    idPool.put(idGenerator.nextId());
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private class Scheduler extends Thread {
        private final BlockingQueue<Long> idPool;

        Scheduler(BlockingQueue<Long> idPool, String name) {
            this.idPool = idPool;
            this.setName(name);
        }

        List<Long> generateIds(int size) throws InterruptedException {
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ids.add(idPool.take());
            }
            return ids;
        }
    }
}
