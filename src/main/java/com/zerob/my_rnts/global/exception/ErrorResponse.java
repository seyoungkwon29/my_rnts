package com.zerob.my_rnts.global.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(
        int status,
        String code,
        String message,
        List<CustomFieldError> errors
) {

    public static ErrorResponse of(final CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(CustomFieldError.listOfCauseError(exception.getCause()))
                .build();
    }

    public ErrorResponse {
        if (status == 0) status = 500;
        if (code == null) code = "API_ERROR";
        if (message == null || message.isBlank()) message = "API 오류";
    }
}
