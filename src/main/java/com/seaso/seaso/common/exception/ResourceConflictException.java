package com.seaso.seaso.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends ServiceException {
    private static final long serialVersionUID = 5995511355805901863L;
}
