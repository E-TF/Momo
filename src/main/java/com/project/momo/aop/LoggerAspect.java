package com.project.momo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Before("execution(!void com.project.momo.common.exception.GlobalExceptionHandler.*(*))")
    public void log(JoinPoint joinPoint) {
        log.info("",joinPoint.getArgs());
    }
}
