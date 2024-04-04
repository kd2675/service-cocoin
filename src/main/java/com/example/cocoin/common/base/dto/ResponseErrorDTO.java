package com.example.cocoin.common.base.dto;

import com.example.cocoin.common.base.vo.Code;

public class ResponseErrorDTO extends ResponseDTO {

    private ResponseErrorDTO(Code errorCode) {
        super(false, errorCode.getCode(), errorCode.getMessage());
    }

    private ResponseErrorDTO(Code errorCode, Exception e) {
        super(false, errorCode.getCode(), errorCode.getMessage(e));
    }

    private ResponseErrorDTO(Code errorCode, String message) {
        super(false, errorCode.getCode(), errorCode.getMessage(message));
    }


    public static ResponseErrorDTO of(Code errorCode) {
        return new ResponseErrorDTO(errorCode);
    }

    public static ResponseErrorDTO of(Code errorCode, Exception e) {
        return new ResponseErrorDTO(errorCode, e);
    }

    public static ResponseErrorDTO of(Code errorCode, String message) {
        return new ResponseErrorDTO(errorCode, message);
    }
}