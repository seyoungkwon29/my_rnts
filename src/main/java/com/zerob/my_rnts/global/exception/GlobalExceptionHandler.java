package com.zerob.my_rnts.global.exception;

import com.zerob.my_rnts.domain.member.exception.MemberErrorCode;
import com.zerob.my_rnts.domain.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = String.format("%s는 %s 타입이어야 합니다.", e.getName(),
                Objects.requireNonNull(e.getRequiredType()).getSimpleName());

        final ErrorResponse response =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "T001", message, null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        String message = "타입 일치하지 않습니다. 확인이 필요합니다.";

        final ErrorResponse response =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "T002", message, null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(e);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        MemberException memberException = new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        final ErrorCode errorCode = memberException.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(memberException);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

}
