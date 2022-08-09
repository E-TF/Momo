package com.project.momo.common.lock;

import lombok.Getter;

@Getter
public enum DistributedLockPrefix {
    CLUB_ID("club_id"),
    CLUB_NAME("club_name"),
    MEMBER_ID("member_id"),
    MEMBER_LOGIN_ID("member_login_id");

    private String value;

    DistributedLockPrefix(String value) {
        this.value = value;
    }
}
