package com.project.momo.common.lock;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DistributedLock {
    DistributedLockPrefix prefix();
}
