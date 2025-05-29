package com.example.cocoin.common.exception;

/**********************************************************************************************
 * @FileName  : ActOrServiceErrorException
 * @Date      : 2024-02-23
 * @작성자      : 방우현
 * @설명       : 작성한 함수 내부에서 에러가 발생할 시, 던지는 예외
 **********************************************************************************************/
public class ActOrServiceErrorException extends RuntimeException {
    public ActOrServiceErrorException() {
    }

    public ActOrServiceErrorException(String message) {
        super(message);
    }

    public ActOrServiceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActOrServiceErrorException(Throwable cause) {
        super(cause);
    }

    public ActOrServiceErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
