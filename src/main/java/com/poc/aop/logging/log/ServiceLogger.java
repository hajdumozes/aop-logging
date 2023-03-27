package com.poc.aop.logging.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogger {
    @Pointcut("within(com.poc.aop.logging.service.*)")
    public void servicePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @AfterThrowing(pointcut = "servicePointcut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.info("Exception: in {}.{}() with exception type = {} and message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), exception.getClass(), exception.getMessage());
    }
}
