package com.project.momo.aop;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.DistributedLockException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockManager;
import com.project.momo.common.lock.LockName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.time.Duration;

@Slf4j
@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class DistributedLockAspect {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1);
    private final DistributedLockManager distributedLockManager;

    @Around("@annotation(distributedLock)")
    public void handleDistributedLock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String lockName = getLockName(joinPoint, distributedLock);
        distributedLockManager.getLock(lockName, DEFAULT_TIMEOUT);
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            BusinessException cause = (BusinessException) throwable.getCause();
            throw cause;
        }
        distributedLockManager.releaseLock(lockName);
    }

    private String getLockName(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String lockName = distributedLock.prefix().getValue();
        boolean hasLockName = false;
        Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (LockName.class.isInstance(annotation)) {
                    hasLockName = true;
                    lockName += args[i];
                    break;
                }
            }
        }
        if (!hasLockName) {
            throw new DistributedLockException(ErrorCode.NO_LOCK_NAME_SET);
        }
        return lockName;
    }
}
