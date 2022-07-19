package com.project.momo.common.lock;

import java.time.Duration;

public interface DistributedLockManager {
    void getLock(String lockName, Duration timeout);

    void releaseLock(String lockName);
}
