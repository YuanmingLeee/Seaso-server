package com.seaso.seaso.modules.question.exception;

import com.seaso.seaso.common.exception.ResourceNotFoundException;

public class AnswerNotFoundException extends ResourceNotFoundException {

    public AnswerNotFoundException() {
        super();
    }

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnswerNotFoundException(Throwable cause) {
        super(cause);
    }

    public AnswerNotFoundException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
