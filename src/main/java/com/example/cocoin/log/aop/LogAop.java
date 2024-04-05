package com.example.cocoin.log.aop;

import com.example.cocoin.log.database.rep.jpa.log.LogEntity;
import com.example.cocoin.log.database.rep.jpa.log.LogRepository;
import com.example.cocoin.service.auth.database.rep.jpa.user.UserDTO;
import com.example.cocoin.service.auth.database.rep.jpa.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAop {
    private final LogRepository logRepository;

    @Pointcut("@annotation(com.example.cocoin.log.annotation.Log)")
    private void logPointcut() {
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "returnObj")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logSuccess(JoinPoint joinPoint, Object returnObj) {
        String className = joinPoint.getSignature().getName();
        String kind = joinPoint.getKind();

        String argsString = Arrays.stream(joinPoint.getArgs())
                .map(
                        v -> {
                            if (v.getClass().equals(UserEntity.class)) {
                                return UserDTO.of((UserEntity) v);
                            } else {
                                return v;
                            }
                        }
                )
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));

        String returnObjToString =
                !returnObj.getClass().equals(Object.class)
                        ? String.valueOf(returnObj)
                        : ToStringBuilder.reflectionToString(returnObj);

        String longString = joinPoint.toLongString();
        String shortString = joinPoint.toShortString();
        log.error("----------LOG----------");

        LogEntity logEntity = LogEntity.builder()
                .className(className)
                .kind(kind)
                .args(argsString)
                .returnObj(returnObjToString)
                .longIntro(longString)
                .shortIntro(shortString)
                .build();

        logRepository.save(logEntity);
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logThrowing(JoinPoint joinPoint, Exception e) {

        String className = joinPoint.getSignature().getName();
        String kind = joinPoint.getKind();

        String argsString = Arrays.stream(joinPoint.getArgs())
                .map(
                        v -> {
                            if (v.getClass().equals(UserEntity.class)) {
                                return UserDTO.of((UserEntity) v);
                            } else {
                                return v;
                            }
                        }
                )
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));

        String returnObjToString = e.getMessage();

        String longString = joinPoint.toLongString();
        String shortString = joinPoint.toShortString();
        log.error("----------LOG----------");

        LogEntity logEntity = LogEntity.builder()
                .className(className)
                .kind(kind)
                .args(argsString)
                .returnObj(returnObjToString)
                .longIntro(longString)
                .shortIntro(shortString)
                .build();

        logRepository.save(logEntity);
    }
}
