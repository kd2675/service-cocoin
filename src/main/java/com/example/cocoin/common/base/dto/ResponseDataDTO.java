package com.example.cocoin.common.base.dto;

import com.example.cocoin.common.base.vo.Code;
import lombok.Getter;

@Getter
public class ResponseDataDTO<T> extends ResponseDTO {

    private final T data;

    private ResponseDataDTO(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private ResponseDataDTO(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> ResponseDataDTO<T> of(T data) {
        return new ResponseDataDTO<>(data);
    }

    public static <T> ResponseDataDTO<T> of(T data, String message) {
        return new ResponseDataDTO<>(data, message);
    }

    public static <T> ResponseDataDTO<T> empty() {
        return new ResponseDataDTO<>(null);
    }
}