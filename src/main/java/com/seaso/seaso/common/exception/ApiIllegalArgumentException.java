package com.seaso.seaso.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiIllegalArgumentException extends ConstraintViolationException {

    public ApiIllegalArgumentException(String message) {
        super(message, null);
    }

    public ApiIllegalArgumentException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }
}
