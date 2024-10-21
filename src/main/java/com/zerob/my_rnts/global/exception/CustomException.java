package com.zerob.my_rnts.global.exception;

public class CustomException extends RuntimeException {

    protected ErrorCode ERROR_CODE;

    private static ErrorCode getDefaultErrorCode() {
        return DefaultErrorCodeHolder.DEFAULT_ERROR_CODE;
    }

    private static class DefaultErrorCodeHolder {
        private static final ErrorCode DEFAULT_ERROR_CODE = new ErrorCode() {
            @Override
            public String name() {
                return "SERVER_ERROR";
            }

            @Override
            public int getStatus() {
                return 500;
            }

            @Override
            public String getCode() {
                return "C001";
            }

            @Override
            public String getMessage() {
                return "서버 요류";
            }

            @Override
            public RuntimeException getException() {
                return new CustomException("SERVER_ERROR");
            }

            @Override
            public RuntimeException getException(Throwable cause) {
                return new CustomException("SERVER_ERROR", cause);
            }
        };
    }

    public CustomException() {
        this.ERROR_CODE = getDefaultErrorCode();
    }

    public CustomException(String message) {
        super(message);
        this.ERROR_CODE = getDefaultErrorCode();
    }


    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.ERROR_CODE = getDefaultErrorCode();
    }


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.ERROR_CODE = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.ERROR_CODE = errorCode;
    }

    public ErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}


// DEFAULT_ERROR_CODE를 만들 때, 클래스 로드 타임에는 자바가 동시성을 보장해 준다는 점을 이용해서, Thread-safe한 지연로딩을 적용했다.
// 이러면 getDefaultErrorCode() 메서드를 처음 사용하는 시점에 DEFAULT_ERROR_CODE를 생성하면서도 Thread-safe가 보장된다.
// ErrorCode를 넣어서 만들 때는 그 에러 코드를 필드로 담지만, 따로 안 넣으면 이 DEFAULT_ERROR_CODE가 담기게 했다.
// 다른 커스텀 예외들도 이 CustomException 클래스를 상속받아서 사용하면 되고,
// 이전에 비교 차원에서 이런 상위타입 예외를 두지 않고 사용해 보았을 때보다
// 당연히 상위 타입을 하나 두고 딱 이곳에서만 ErrorCode를 받아다 관리하게 두는 것이 하위 타입 구현에 훨씬 편리하다.