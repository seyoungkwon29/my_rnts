package com.zerob.my_rnts.global.exception;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record CustomFieldError (@NonNull String field, @NonNull String message) {

    public static List<CustomFieldError> listOfCauseError(Throwable cause) {
        return List.of(arrayOfCauseError(cause));
    }

    public static CustomFieldError[] arrayOfCauseError(Throwable cause) {
        int depth = 0;
        CustomFieldError[] subErrors;
        Throwable currentCause = cause;

        while (currentCause != null) {
            currentCause = currentCause.getCause();
            depth++;
        }

        subErrors = new CustomFieldError[depth];
        currentCause = cause;

        for (int i = 0; i < depth; i++) {
            String errorFullName = currentCause.getClass().getSimpleName();
            String field = errorFullName.substring(errorFullName.lastIndexOf('.') + 1);
            subErrors[i] = CustomFieldError.builder()
                    .field(field)
                    .message(currentCause.getLocalizedMessage())
                    .build();

            currentCause = currentCause.getCause();
        }

        return subErrors;
    }
}
