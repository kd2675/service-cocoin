package com.example.cocoin.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TimerAop { // Timer 동작 AOP

    // annotation 패키지 하위의 Timer method 는 **로깅하기**  ->  실행 시간이 필요함
    @Pointcut("@annotation(com.example.cocoin.common.annotation.Timer)")
    private void timerPointcut() {
    }

    // **Before, After Method 는 Timer 를 공유할 수 없음**
    // cut() 과 enableTimer() 를 같이 사용
    @Around("timerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 실행 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // joinPoint.proceed() -> 실질적인 method 실행
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }finally {
            stopWatch.stop();
            // 총 걸린 시간 (초단위)

            log.warn("----------execution = {}----------", joinPoint.toShortString());
//            log.error("----------class = {}----------", joinPoint.getSignature().getName());
            log.warn("----------totalTime = {}s----------", (double) Math.round(stopWatch.getTotalTimeSeconds() * 10000) / 10000);
        }
//        Object result = joinPoint.proceed();
//        // 실행 후
//        stopWatch.stop();
//        // 총 걸린 시간 (초단위)
//
//        log.error("----------class = {}----------", joinPoint.getSignature().getName());
//        log.error("----------totalTime = {}s----------", (double) Math.round(stopWatch.getTotalTimeSeconds() * 10000) / 10000);
    }

}