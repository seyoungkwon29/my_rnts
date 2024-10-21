package com.zerob.my_rnts.global.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),
    ACCESS_DENIED(403, "C002", "해당 자원에 대한 접근 권한이 없습니다."),
    INVALID_INPUT_VALUE(400, "C003", "잘못된 입력입니다.");

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
        return new CommonException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new CommonException(this, cause);
    }
}
