package com.example.cocoin.common.base.vo;

import com.example.cocoin.common.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Code {

    // 충돌 방지를 위한 Code format
    // X1XXX: 제이
    // X2XXX: 셀리나
    // X3XXX: 메이슨
    // ex) 메이슨이 닉네임 중복 에러코드를 만든다면
    // USER_NICKNAME_DUPLICATED(13010, HttpStatus.BAD_REQUEST, "User nickname duplicated"),

    OK(1, HttpStatus.OK, "Ok"),

    BAD_REQUEST(40000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(40001, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_MATCH_PASSWORD(40002, HttpStatus.BAD_REQUEST, "Not match password"),
    NO_SEARCH_USER(40003, HttpStatus.FORBIDDEN, "No search user"),
    NOT_FOUND(40004, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    NOT_ENOUGH_POINT(40020, HttpStatus.PAYMENT_REQUIRED, "Not Enough point"),
    NO_SEARCH_ORDER(40021, HttpStatus.NOT_FOUND, "No search order"),

    UNAUTHORIZED(40070, HttpStatus.UNAUTHORIZED, "User unauthorized"),
    TOKEN_SIGNATURE(40071, HttpStatus.UNAUTHORIZED, "SignatureException"),
    TOKEN_MALFORMED(40072, HttpStatus.UNAUTHORIZED, "MalformedException"),
    TOKEN_EXPIRED(40073, HttpStatus.UNAUTHORIZED, "ExpiredException"),
    TOKEN_UNSUPPORTED(40074, HttpStatus.UNAUTHORIZED, "UnsupportedException"),
    TOKEN_ILLEGAL_ARGUMENT(40075, HttpStatus.FORBIDDEN, "IllegalArgumentException"),

    INTERNAL_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
//        return Optional.ofNullable(message)
//                .orElse(this.getMessage());
        return Optional.ofNullable(message)
                .filter(v->!v.isEmpty())
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code.INTERNAL_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}