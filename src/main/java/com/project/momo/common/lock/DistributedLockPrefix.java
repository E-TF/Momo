package com.project.momo.common.lock;

public enum DistributedLockPrefix {
    CLUB_NAME("club_name");

    private String value;

    DistributedLockPrefix(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
