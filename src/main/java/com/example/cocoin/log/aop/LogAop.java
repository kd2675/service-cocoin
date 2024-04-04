package com.example.cocoin.log.aop;

import com.example.cocoin.service.auth.database.rep.jpa.user.UserDTO;
import com.example.cocoin.service.auth.database.rep.jpa.user.UserEntity;
import com.example.cocoin.log.database.rep.jpa.log.LogEntity;
import com.example.cocoin.log.database.rep.jpa.log.LogRepository;
import com.example.cocoin.log.database.rep.jpa.order.LogOrderEntity;
import com.example.cocoin.log.database.rep.jpa.order.LogOrderRepository;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAop {
    private final LogRepository logRepository;
    private final LogOrderRepository logOrderRepository;

    @Pointcut("@annotation(com.example.cocoin.log.annotation.Log)")
    private void logPointcut() {
    }

    @Pointcut("@annotation(com.example.cocoin.log.annotation.LogOrder)")
    private void logOrderPointcut() {
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "returnObj")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void orderSuccess(JoinPoint joinPoint, Object returnObj) {
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
    public void orderThrowing(JoinPoint joinPoint, Exception e) {

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

    @AfterReturning(pointcut = "logOrderPointcut()", returning = "returnObj")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logOrderSuccess(JoinPoint joinPoint, OrderDTO returnObj) {
        //주문자
        String userString = Arrays.stream(joinPoint.getArgs())
                .filter(v -> v.getClass().equals(UserEntity.class))
                .map(v -> UserDTO.of((UserEntity) v))
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));
        //주문내역
        String argsString = Arrays.stream(joinPoint.getArgs())
                .filter(v -> !v.getClass().equals(UserEntity.class))
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));
        //결과
        String returnObjToString =
                !returnObj.getClass().equals(Object.class)
                        ? String.valueOf(returnObj)
                        : ToStringBuilder.reflectionToString(returnObj);

        LogOrderEntity logOrderEntity = LogOrderEntity.builder()
                .logGiven(userString)
                .logWhen(argsString)
                .logThen(returnObjToString)
                .build();
        logOrderRepository.save(logOrderEntity);
    }

    @AfterThrowing(pointcut = "logOrderPointcut()", throwing = "e")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logOrderThrowing(JoinPoint joinPoint, Exception e) {
        //주문자
        String userString = Arrays.stream(joinPoint.getArgs())
                .filter(v -> v.getClass().equals(UserEntity.class))
                .map(v -> UserDTO.of((UserEntity) v))
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));
        //주문내역
        String argsString = Arrays.stream(joinPoint.getArgs())
                .filter(v -> !v.getClass().equals(UserEntity.class))
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("||"));
        //결과
        String returnObjToString = e.getMessage();

        LogOrderEntity logOrderEntity = LogOrderEntity.builder()
                .logGiven(userString)
                .logWhen(argsString)
                .logThen(returnObjToString)
                .build();
        logOrderRepository.save(logOrderEntity);
    }


}
