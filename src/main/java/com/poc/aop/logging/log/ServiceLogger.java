package com.poc.aop.logging.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ServiceLogger {
    //    within is used to match all the JoinPoint methods in a given class, package, or sub-package.
    @Pointcut("within(com.poc.aop.logging.service.*)")
    public void servicePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    //     execution is used to match a joinPoint method’s signature.
    //    (..) = zero or more parameters
    @Pointcut("execution(public void com.poc.aop.logging.service.*.*(..))")
    public void voidServiceMethods() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    //     execution is used to match a joinPoint method’s signature.
    //    (Entity) = one method parameter, which is of type Entity
    @Pointcut("execution(public * com.poc.aop.logging.service.EntityService.*(com.poc.aop.logging.entity.Entity))")
    public void operationWithEntity() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    //     execution is used to match a joinPoint method’s signature.
    //    (..) = zero or more parameters
    @Pointcut("execution(public java.util.Optional<com.poc.aop.logging.entity.Entity> com.poc.aop.logging.service.EntityService.*(..))")
    public void operationResultingEntity() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @AfterThrowing(pointcut = "servicePointcut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.info("Exception: in {}.{}() with exception type = {} and message = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), exception.getClass(), exception.getMessage());
    }

    @After("voidServiceMethods()")
    public void logDataChange(JoinPoint joinPoint) {
        log.info("Repository data change happened due to {}.{} with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @After("operationWithEntity() || operationResultingEntity()")
    public void entityOperations(JoinPoint joinPoint) {
        log.info("Entity operation: {}.{} with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
}
