package com.zerob.my_rnts.domain.appointment.exception;

import com.zerob.my_rnts.global.exception.CustomException;
import com.zerob.my_rnts.global.exception.ErrorCode;

public class AppointmentException extends CustomException {

    public AppointmentException() {
        super();
    }

    public AppointmentException(String message) {
        super(message);
    }

    public AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AppointmentException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
