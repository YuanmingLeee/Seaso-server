package com.seaso.seaso.modules.media.manager;

import com.seaso.seaso.modules.media.entity.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Fast DFS Manager to provide ...
 *
 * @author puresmileit, Li Yuanming
 * @version 0.1
 */
@Service
public class FastDFSManager implements LargeBinaryFileManager {

    private Logger logger = LoggerFactory.getLogger(FastDFSManager.class);

    private String trackerUrl;

    private TrackerClient trackerClient;

    private TrackerServer trackerServer;

    private StorageServer storageServer;

    public FastDFSManager() {
        try {
            String filePath = new ClassPathResource("fdfs_client.properties").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            trackerUrl = "http://" + trackerServer.getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port() + "/";
        } catch (Exception e) {
            logger.error("FastDFS client init fail.", e);
        }
    }

    @NotNull
    @Contract(value = " -> new", pure = true)
    private StorageClient getStorageClient() {
        return new StorageClient(trackerServer, storageServer);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String uploadFile(@NotNull MultipartFile file) {

        String fileName = file.getOriginalFilename();
        byte[] fileBuff;
        try {
            InputStream ins = file.getInputStream();

            if (ins == null) {
                throw new IOException("Input stream cannot be open");
            }

            fileBuff = new byte[ins.available()];
            ins.read(fileBuff);
            ins.close();
        } catch (IOException e) {
            logger.error("Read file fail.", e);
            throw new RuntimeException(e);
        }

        FastDFSFile fdfsFile = new FastDFSFile(Objects.requireNonNull(fileName), fileBuff);

        logger.info("File name: " + file.getName() + " File length: " + fdfsFile.getLength());

        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", fdfsFile.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResult = null;
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            uploadResult = storageClient.upload_file(fdfsFile.getContent(), fdfsFile.getExt(), meta_list);
        } catch (IOException e) {
            logger.error("IO Exception when uploading the file: " + fdfsFile.getName(), e);
        } catch (Exception e) {
            logger.error("Unknown exception happened when uploading the file: " + fdfsFile.getName(), e);
        }
        // success
        logger.info("Upload file in " + (System.currentTimeMillis() - startTime) + " ms");

        if (uploadResult == null) {
            logger.error("Upload file fail, error code: " + Objects.requireNonNull(storageClient).getErrorCode());
            throw new RuntimeException("Fast DFS client fail to upload file: " + fdfsFile.getName());
        }

        String groupName = uploadResult[0];
        String remoteFileName = uploadResult[1];
        String relativePath = groupName + "/" + remoteFileName;

        logger.info("Upload file successfully as " + relativePath);
        return getTrackerUrl() + relativePath;
    }

    @Nullable
    public FileInfo getFile(String groupName, String remoteFileName) {
        StorageClient storageClient;
        try {
            storageClient = getStorageClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception when getting the file: " + remoteFileName, e);
        } catch (Exception e) {
            logger.error("Unknown exception happened when getting the file: " + remoteFileName, e);
        }
        return null;
    }

    @Nullable
    public InputStream downloadFile(String groupName, String remoteFileName) {
        StorageClient storageClient;
        try {
            storageClient = getStorageClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (IOException e) {
            logger.error("IO Exception when getting the file: " + remoteFileName, e);
        } catch (Exception e) {
            logger.error("Unknown exception happened when getting the file: " + remoteFileName, e);
        }
        return null;
    }

    public void deleteFile(String groupName, String remoteFileName) throws Exception {
        StorageClient storageClient = getStorageClient();
        int i = storageClient.delete_file(groupName, remoteFileName);
        if (i == 0) {
            logger.info("Delete file successfully");
        } else {
            logger.error("Delete file fail, error code: " + i);
            throw new RuntimeException("Delete file fail with error code: " + i);
        }
    }

    public StorageServer[] getStoreStorages(String groupName) throws IOException {
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    @Contract(pure = true)
    private String getTrackerUrl() {
        return trackerUrl;
    }
}
