package com.zerob.my_rnts.global.exception;

public class CommonException extends CustomException {

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CommonException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
