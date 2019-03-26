package com.seaso.seaso.common.exception;

/**
 * Service Tier public exception. This exception is caused by any exception happens during services and trigger the
 * rollback of a transaction.
 *
 * @author Li Yuanming
 * @version 0.0.1
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 5685725309076938368L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
