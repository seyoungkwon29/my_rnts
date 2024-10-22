package com.zerob.my_rnts.domain.penalty.exception;

import com.zerob.my_rnts.global.exception.CustomException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class PenaltyException extends CustomException {

    public PenaltyException() {
        super();
    }

    public PenaltyException(String message) {
        super(message);
    }

    public PenaltyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PenaltyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PenaltyException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
