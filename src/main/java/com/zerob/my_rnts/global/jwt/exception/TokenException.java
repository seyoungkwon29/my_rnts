package com.zerob.my_rnts.global.jwt.exception;

import com.zerob.my_rnts.global.exception.CustomException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class TokenException extends CustomException {

    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
