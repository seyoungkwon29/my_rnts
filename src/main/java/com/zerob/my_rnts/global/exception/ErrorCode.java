package com.zerob.my_rnts.global.exception;

public interface ErrorCode {
    String name();
    int getStatus();
    String getCode();
    String getMessage();
    RuntimeException getException();
    RuntimeException getException(Throwable cause);
}