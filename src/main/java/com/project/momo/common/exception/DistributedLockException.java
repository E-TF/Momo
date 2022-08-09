package com.project.momo.common.exception;

import lombok.Getter;

@Getter
public class DistributedLockException extends RuntimeException {
    public DistributedLockException(Throwable cause) {
        super(cause);
    }

    public DistributedLockException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
