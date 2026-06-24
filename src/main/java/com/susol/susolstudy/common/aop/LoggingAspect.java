package com.susol.susolstudy.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.susol.susolstudy.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        long start = System.currentTimeMillis();
        log.info("[START] {}", methodName);

        try {
            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis() - start;
            log.info("[END]   {} ({}ms)", methodName, end);

            return result;
        } catch(Throwable e) {
            long end = System.currentTimeMillis() - start;
            log.error("[ERROR] {} ({}ms) - {}", methodName, end, e.getMessage());
            throw e;
        }
    }
}
