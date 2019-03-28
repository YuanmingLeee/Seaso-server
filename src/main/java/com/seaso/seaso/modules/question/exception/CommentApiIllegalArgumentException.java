package com.seaso.seaso.modules.question.exception;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;

public class CommentApiIllegalArgumentException extends ApiIllegalArgumentException {

    public CommentApiIllegalArgumentException() {
        super();
    }

    public CommentApiIllegalArgumentException(String message) {
        super(message);
    }

    public CommentApiIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentApiIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public CommentApiIllegalArgumentException(String message, Throwable cause,
                                              boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
