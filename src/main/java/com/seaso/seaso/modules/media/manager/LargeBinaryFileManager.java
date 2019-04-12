package com.seaso.seaso.modules.media.manager;

import org.csource.fastdfs.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface LargeBinaryFileManager {
    String uploadFile(MultipartFile file);

    FileInfo getFile(String groupName, String remoteFileName);

    InputStream downloadFile(String groupName, String remoteFileName);

    void deleteFile(String groupName, String remoteFileName) throws Exception;
}
