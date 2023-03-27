package com.poc.aop.logging.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogger {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void requestPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("requestPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "requestPointcut()", returning = "response")
    public void logAfter(JoinPoint joinPoint, ResponseEntity<?> response) {
        log.info("Exit: {}.{}() with status code = {} and body = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), response.getStatusCode(), response.getBody());
    }
}
