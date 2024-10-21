package com.zerob.my_rnts.global.jwt.exception;

import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    // TOKEN
    TOKEN_NOT_FOUND(401, "J001", "잘못된 토큰입니다."),
    TOKEN_EXPIRED(401, "J002", "토큰이 만료되었습니다."),
    TOKEN_INVALID(401, "J003", "토큰이 유효하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public RuntimeException getException() {
        return new MemberException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new MemberException(this, cause);
    }
}
