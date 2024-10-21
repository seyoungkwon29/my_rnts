package com.zerob.my_rnts.domain.appointment.exception;

import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppointmentErrorCode implements ErrorCode {

    // APPOINTMENT
    APPOINTMENT_NOT_FOUND(404, "A001", "약속이 존재하지 않습니다."),
    CUSTOM_TYPE_NOT_FOUND(404, "A002", "사용자가 정의한 약속 유형이 존재하지 않습니다.");

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
        return new AppointmentException(this);
    }

    @Override
    public RuntimeException getException(Throwable cause) {
        return new AppointmentException(this, cause);
    }
}
