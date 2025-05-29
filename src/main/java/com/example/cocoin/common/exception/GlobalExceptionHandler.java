package com.example.cocoin.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.example.core.response.base.dto.ResponseErrorDTO;
import org.example.core.response.base.exception.GeneralException;
import org.example.core.response.base.vo.Code;
import org.example.core.utils.ClientUtil;
import org.example.core.utils.ServerTypeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler
//    public ResponseEntity<Object> exception(
//            Exception e,
//            WebRequest request
//    ) {
//        log.error("----------Exception----------=> {}", e);
//        return handleExceptionInternal(e, Code.INTERNAL_SERVER_ERROR, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException e,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request
//    ) {
//        log.error("    URL   => " + request.getContextPath() + " | HttpMessageNotReadableException Error | NONE 처리 됐음");
//        return handleExceptionInternal(e, Code.INTERNAL_SERVER_ERROR, request);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
//        log.error("----------ConstraintViolationException----------=> {}", e);
//        return handleExceptionInternal(e, Code.VALIDATION_ERROR, request);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
//        if (ServerTypeUtils.isProd()) {
//            log.error("----------GeneralException----------=> {}", e);
//        }
//        return handleExceptionInternal(e, e.getErrorCode(), request);
//    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(),
                request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                ResponseErrorDTO.of(errorCode, errorCode.getMessage(e)),
                headers,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleExceptionInternal(e, body, headers, statusCode, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> defaultErrorHandler(
            Exception e,
            HttpServletRequest request,
            HttpServletResponse response,
            WebRequest webRequest
    ) {
        StringBuilder sb = new StringBuilder();

        String deviceInfo = ClientUtil.getDeviceInfo(request);
        String clientIP = ClientUtil.getClientIP(request);

        sb.append("\n=======================================================\n")
                .append("URL : ").append(request.getRequestURL()).append("\n")
                .append("deviceInfo  : ").append(deviceInfo.toUpperCase()).append("\n")
                .append("clientIP : ").append(clientIP).append("\n")
                .append("stack trace : ").append(ExceptionUtils.getStackTrace(e)).append("\n")
                .append("=======================================================");
        log.error("{}", sb);
        if (response.isCommitted() || e.getCause() instanceof ClientAbortException) {
            return null;
        } else {
            return handleExceptionInternal(e, Code.VALIDATION_ERROR, webRequest);
        }
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e,
            HttpServletRequest request,
            HttpServletResponse response,
            WebRequest webRequest
    ) {
        StringBuilder sb = new StringBuilder();
        String deviceInfo = ClientUtil.getDeviceInfo(request);
        String clientIP = ClientUtil.getClientIP(request);
        sb.append("\n=======================================================\n")
                .append("URL : ").append(request.getRequestURL()).append("\n")
                .append(" | HttpMessageNotReadableException Error | NONE 처리 됐음\n")
                .append("deviceInfo  : ").append(deviceInfo.toUpperCase()).append("\n")
                .append("clientIP : ").append(clientIP).append("\n")
                .append("stack trace : ").append(ExceptionUtils.getStackTrace(e)).append("\n")
                .append("=======================================================");
        log.warn("{}", sb);
        if (response.isCommitted() || e.getCause() instanceof ClientAbortException) {
            return null;
        } else {
            return handleExceptionInternal(e, Code.VALIDATION_ERROR, webRequest);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Object> generalErrorHandler(
            GeneralException e,
            HttpServletRequest request,
            HttpServletResponse response,
            WebRequest webRequest
    ) {
        StringBuilder sb = new StringBuilder();

        String deviceInfo = ClientUtil.getDeviceInfo(request);
        String clientIP = ClientUtil.getClientIP(request);

        sb.append("\n=======================================================\n")
                .append("URL : ").append(request.getRequestURL()).append("\n")
                .append("GeneralException\n")
                .append("deviceInfo  : ").append(deviceInfo.toUpperCase()).append("\n")
                .append("clientIP : ").append(clientIP).append("\n")
                .append("stack trace : ").append(ExceptionUtils.getStackTrace(e)).append("\n")
                .append("=======================================================");
        log.error("{}", sb);
        if (response.isCommitted() || e.getCause() instanceof ClientAbortException) {
            return null;
        } else {
            return handleExceptionInternal(e, Code.VALIDATION_ERROR, webRequest);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationErrorHandler(
            MethodArgumentNotValidException e,
            HttpServletRequest request,
            WebRequest webRequest
    ) {
        StringBuilder sb = new StringBuilder();

        String deviceInfo = ClientUtil.getDeviceInfo(request);
        String clientIP = ClientUtil.getClientIP(request);
        sb.append("\n=======================================================\n")
                .append("URL : ").append(request.getRequestURL()).append("\n")
                .append("ValidationError\n")
                .append("deviceInfo  : ").append(deviceInfo.toUpperCase()).append("\n")
                .append("clientIP : ").append(clientIP).append("\n")
                .append("parameters : \n")
                .append(" class : ").append(e.getObjectName()).append("\n");

        e.getFieldErrors().forEach(field ->
                sb.append("\t")
                        .append(field.getField())
                        .append("=")
                        .append(field.getRejectedValue())
                        .append(" : ")
                        .append(field.getDefaultMessage())
                        .append("\n")
        );

        sb.append("=======================================================");
        log.error("{}", sb);
        return handleExceptionInternal(e, Code.VALIDATION_ERROR, webRequest);
    }

    @ExceptionHandler(ActOrServiceErrorException.class)
    public ResponseEntity<Object> actOrServiceErrorHandler(
            ActOrServiceErrorException e,
            WebRequest webRequest
    ) {
        return handleExceptionInternal(e, Code.VALIDATION_ERROR, webRequest);
    }
}