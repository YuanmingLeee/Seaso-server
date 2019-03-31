package com.seaso.seaso.common.utils.idgen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * A ID generating service is an instance of service which generates IDs using different algorithm by IdGenerator. To
 * use it for generating ID service, we shall create another class which extends IdService, and register it
 * into Spring context holder.
 * <pre>{@code
 *     \@Service
 *     class GenerateUserIdService extends IdService {
 *
 *         \@Autowired
 *         \@Qualifier("someGenerator")
 *         public IdGeneratorService(IdGeneratable idGenerator) {
 *              super(idGenerator, 1024);
 *         }
 *     }
 * }</pre>
 * <p><code>IdService</code> creates two threads when registered: {@link #generator} and
 * <code>scheduler</code>. Insides, a {@link BlockingQueue} is used for caching IDs.
 *
 * <p>
 * By the time the service is registered, it will auto generated certain number of IDs in cache. This is done by its
 * generator thread, which fills in the cache and gets blocked. The wait state can only be cleared when there is query
 * of IDs by scheduler.
 * <p>To generate a batch of IDs, we need to call {@link #generateIds(int)} method. However, the returned ID list is not
 * always guaranteed.
 * <p>To stop the Service, you can explicitly call {@link #stop()} method, or wait the stop of the parent thread.
 *
 * <p>This class is Interrupted free, which will not raise any {@link InterruptedException}
 *
 * @author Li Yuanming
 * @version 0.1
 * @see IdGeneratable
 * @see InterruptedException
 * @see #generateIds(int)
 * @see #stop()
 */
public class IdService {

    /* */
    private IdGenerationThread generator;

    private Scheduler scheduler;

    /**
     * Constructor of IdService with a given cache size of IDs. The service is automatically started when
     * created. <b>Manually start by {@link #start()} shall not be involved</b>.
     *
     * @param idGenerator ID generator implemented using specific algorithm
     * @param cacheSize   number of IDs cached by the ID generating server
     */
    public IdService(IdGeneratable idGenerator, int cacheSize) {
        BlockingQueue<Long> idPool = new ArrayBlockingQueue<>(cacheSize);
        this.generator = new IdGenerationThread(idGenerator, idPool, "generator");
        this.scheduler = new Scheduler(idPool, "scheduler");

        this.start();
    }

    /**
     * Generate a list of IDs with a given size. {@link RuntimeException} is thrown if {@link #scheduler} is
     * interrupted during fetching IDs from cache.
     *
     * @param size number of IDs to be generated
     * @return list of IDs. {@link InterruptedException} is raised when the service get interrupted when generating IDs.
     */
    public List<Long> generateIds(int size) {
        try {
            return scheduler.generateIds(size);
        } catch (InterruptedException e) {
            throw new RuntimeException("Fetching ID error.", e.getCause());
        }
    }

    /**
     * Start the ID generating service. This method is called during construction and shall not be manually called after
     * construction. However, it shall be called if the ID generating service is restarted.
     *
     * @see #IdService(IdGeneratable, int)
     */
    public void start() {
        generator.start();
        scheduler.start();
    }

    /**
     * Stop the ID generating service. This operating is not required, as the child thread will stopped with termination
     * of it parent thread.
     */
    public void stop() {
        generator.interrupt();
        scheduler.interrupt();
    }

    /**
     * A ID generation thread is a {@link Thread} in charge of repeatedly generating IDs when the ID pool is not filled.
     * The behavior of this Thread is controlled by the {@link BlockingQueue} {@link #idPool}. When pool is filled, this
     * thread will be blocked. When there is a vacancy in {@link #idPool}, the thread will keep generating ID.
     *
     * @see BlockingQueue
     */
    private class IdGenerationThread extends Thread {

        /**
         * ID generator implemented by specific algorithm
         */
        private final IdGeneratable idGenerator;

        /**
         * A cache of ID
         */
        private final BlockingQueue<Long> idPool;

        /**
         * Constructor of ID generation thread given specific ID generator, its thread name and the ID pool.
         *
         * @param idGenerator ID generator implemented by specific algorithm
         * @param idPool      a cache of ID, should be an instance of {@link BlockingQueue}
         * @param name        name of the thread
         */
        IdGenerationThread(IdGeneratable idGenerator, BlockingQueue<Long> idPool, String name) {
            this.idGenerator = idGenerator;
            this.setName(name);
            this.setPriority(Thread.MAX_PRIORITY);
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

    /**
     * A scheduler thread is a {@link Thread} which takes care of the fetch of IDs when {@link #idPool} is not empty.
     * The thread is blocked when the {@link #idPool} is empty.
     */
    private class Scheduler extends Thread {

        /**
         * A cache of ID
         */
        private final BlockingQueue<Long> idPool;

        /**
         * Constructor of Scheduler thread given thread name and the ID pool
         *
         * @param idPool a cache of ID
         * @param name   name o the thread
         */
        Scheduler(BlockingQueue<Long> idPool, String name) {
            this.idPool = idPool;
            this.setName(name);
        }

        /**
         * @param size number of IDs to be generated
         * @return a list of fetched ID if the thread is not interrupted when fetching from {@link #idPool}
         * @throws InterruptedException {@inheritDoc}
         */
        List<Long> generateIds(int size) throws InterruptedException {
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ids.add(idPool.take());
            }
            return ids;
        }
    }
}
