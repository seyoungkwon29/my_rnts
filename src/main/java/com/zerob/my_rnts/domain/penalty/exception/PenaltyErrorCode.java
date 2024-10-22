package com.zerob.my_rnts.domain.penalty.exception;

import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PenaltyErrorCode implements ErrorCode {

    PENALTY_NOT_FOUND(404, "P001", "해당 패널티가 존재하지 않습니다.");

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
        return new PenaltyException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new PenaltyException(this, cause);
    }
}
