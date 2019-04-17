package com.seaso.seaso.modules.media.web;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;
import com.seaso.seaso.modules.media.manager.LargeBinaryFileManager;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author puresmileit, Li Yuanming
 * @version 0.1
 */
@RestController
@RequestMapping("bin")
public class BinFileController {

    private LargeBinaryFileManager largeBinaryFileManager;

    @Autowired
    public BinFileController(LargeBinaryFileManager largeBinaryFileManager) {
        this.largeBinaryFileManager = largeBinaryFileManager;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> singleFileUpload(@RequestParam MultipartFile file) {

        String message;
        if (file.isEmpty()) {
            message = "Please select a file to upload";
            throw new ApiIllegalArgumentException(message);
        }

        String path = largeBinaryFileManager.uploadFile(file);
        message = "File `" + file.getOriginalFilename() + "` has been uploaded";

        return new ResponseEntity<>(new JsonResponseBody<>(message, 201, path), HttpStatus.CREATED);
    }
}
