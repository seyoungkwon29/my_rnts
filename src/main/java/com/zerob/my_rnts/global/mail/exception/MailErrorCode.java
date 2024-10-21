package com.zerob.my_rnts.global.mail.exception;

import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailErrorCode implements ErrorCode {

    // MAIL
    MAIL_NOT_FOUND(404, "E000", "해당 메일로 가입된 사용자가 없습니다."),
    VERIFICATION_FAILED(401, "E001", "인증 실패했습니다."),
    LOGINID_NOT_FOUND(404, "E002", "해당 이메일로 등록된 아이디가 존재하지 않습니다.");

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
        return new MailException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new MailException(this, cause);
    }
}
