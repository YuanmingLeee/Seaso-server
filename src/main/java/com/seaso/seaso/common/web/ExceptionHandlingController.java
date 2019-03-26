package com.seaso.seaso.common.web;

import com.seaso.seaso.common.exception.ResourceConflictException;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionHandlingController {
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceConflictException.class)
    public JsonResponse<String> resourceConflict() {
        return new JsonResponse<>(HttpStatus.CONFLICT, "Resources conflict", "");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public JsonResponse<String> resourceNotFound() {
        return new JsonResponse<>(HttpStatus.NOT_FOUND, "Resources not found", "");
    }
}
