package com.seaso.seaso.common.exception;

public class ApiIllegalArgumentException extends ServiceException {

    public ApiIllegalArgumentException() {
        super();
    }

    public ApiIllegalArgumentException(String message) {
        super(message);
    }

    public ApiIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public ApiIllegalArgumentException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
