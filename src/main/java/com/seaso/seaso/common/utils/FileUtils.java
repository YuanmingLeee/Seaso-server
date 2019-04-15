package com.seaso.seaso.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtils {
    public static File multipartFileToFile(MultipartFile mpf) {
        File f = new File(Objects.requireNonNull(mpf.getOriginalFilename()));
        try {
            assert f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(mpf.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }
}
