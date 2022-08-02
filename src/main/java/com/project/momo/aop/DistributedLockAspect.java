package com.project.momo.aop;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockManager;
import com.project.momo.common.lock.LockName;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.time.Duration;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class DistributedLockAspect {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(3);
    private final DistributedLockManager distributedLockManager;

    @Around("@annotation(distributedLock)")
    public Object handleDistributedLock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String lockName = getLockName(joinPoint, distributedLock);
        distributedLockManager.getLock(lockName, DEFAULT_TIMEOUT);
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw (BusinessException) throwable;
        } finally {
            distributedLockManager.releaseLock(lockName);
        }
    }

    private String getLockName(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String lockName = distributedLock.prefix().getValue();
        Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof LockName) {
                    lockName += args[i];
                    break;
                }
            }
        }
        return lockName;
    }
}
