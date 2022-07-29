package com.project.momo.common.lock;

import lombok.Getter;

@Getter
public enum DistributedLockPrefix {
    CLUB_NAME("club_name");

    private String value;

    DistributedLockPrefix(String value) {
        this.value = value;
    }
}
