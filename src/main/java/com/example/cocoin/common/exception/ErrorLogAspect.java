package com.example.cocoin.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.core.utils.ClientUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ErrorLogAspect {

    @Pointcut("execution(* com.example.cocoin.service.cocoin..*Act*.*(..)) || execution(* com.example.cocoin.service.cocoin..*Controller*.*(..))")
    public void actExecution() {}

    @AfterThrowing(pointcut = "actExecution()", throwing = "e")
    public void errorLog(JoinPoint joinPoint, Exception e) {
        Signature classSignature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = classSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] parameters = joinPoint.getArgs();

        ErrorLogAspect.writeErrorLog(className, methodName, parameters, e);
        throw new ActOrServiceErrorException();
    }

    public static void writeErrorLog(String className, String methodName, Object[] parameters, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String deviceInfo = ClientUtil.getDeviceInfo(request);

        StringBuilder sb = new StringBuilder();

        sb.append("\n=======================================================\n")
                .append("deviceInfo  : ").append(deviceInfo.toUpperCase()).append("\n")
                .append("class name  : ").append(className).append("\n")
                .append("method name : ").append(methodName).append("\n")
                .append("method parameters : ").append("\n");

        Arrays.stream(parameters)
                .forEach(parameter -> sb.append("\t").append(parameter).append("\n"));

        sb.append("stack trace : ").append(ErrorLogAspect.getLineLimitedStackTraceString(e));
        sb.append("=======================================================");

        log.error("{}", sb);
    }

    private static String getLineLimitedStackTraceString(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        Arrays.stream(e.getStackTrace())
                .limit(10)
                .forEach(ste -> sb.append(ste).append("\n"));
        return sb.toString();
    }
}
