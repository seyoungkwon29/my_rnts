package com.zerob.my_rnts.domain.member.exception;

import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    // MEMBER
    PASSWORD_NULL_ERROR(400, "M001", "비밀번호가 입력되지 않았습니다."),
    MEMBER_NOT_FOUND(404, "M002", "해당 사용자가 존재하지 않습니다."),
    DUPLICATED_LOGINID(401, "M003", "이미 존재하는 아이디 입니다."),
    DUPLICATED_MAIL(401, "M004", "이미 존재하는 메일 입니다."),
    DUPLICATED_NICKNAME(401, "M005", "이미 존재하는 닉네임 입니다."),
    DUPLICATED_PASSWORD(400, "M006", "기존 비밀번호와 동일합니다."),
    MISMATCH_PASSWORD(400, "M007", "비밀번호가 일치하지 않습니다.");

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
