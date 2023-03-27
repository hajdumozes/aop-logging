package com.poc.aop.logging.log;

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
public class ExecutionTimeLogger {

    //     annotation is used to mark methods which have the following annotations
    @Pointcut("@annotation(com.poc.aop.logging.log.LogExecutionTime)")
    public void logExecutionTimeAnnotations() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    //    @Around is an advice type, which ensures that an advice can run before and after the method execution
    @Around("logExecutionTimeAnnotations()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("\"{}\" executed in {} ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());
        return proceed;
    }

}
