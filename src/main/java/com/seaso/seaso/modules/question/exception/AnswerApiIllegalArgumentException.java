package com.seaso.seaso.modules.question.exception;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;

public class AnswerApiIllegalArgumentException extends ApiIllegalArgumentException {
    public AnswerApiIllegalArgumentException() {
        super();
    }

    public AnswerApiIllegalArgumentException(String message) {
        super(message);
    }

    public AnswerApiIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnswerApiIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public AnswerApiIllegalArgumentException(String message, Throwable cause,
                                             boolean enableSuppression,
                                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
