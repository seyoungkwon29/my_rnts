package com.zerob.my_rnts.global.file.exception;

import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

    // FILE
    FAILED_TO_UPLOAD_FILE(401, "F001", "파일 업로드를 실패했습니다."),
    UNSUPPORTED_FILE_TYPE(415, "F002", "지원하지 않는 확장자입니다.");

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
        return new FileException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new FileException(this, cause);
    }
}
