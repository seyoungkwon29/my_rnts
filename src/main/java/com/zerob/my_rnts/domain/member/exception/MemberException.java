package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.CustomException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class MemberException extends CustomException {

    public MemberException() {
        super();
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
