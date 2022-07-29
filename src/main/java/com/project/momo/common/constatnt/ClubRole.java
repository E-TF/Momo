package com.project.momo.common.constatnt;

import lombok.Getter;

@Getter
public enum ClubRole {
    MANAGER(1),
    MEMBER(0);

    private int level;

    ClubRole(int level) {
        this.level = level;
    }

    boolean hasManagerRole(ClubRole role) {
        return role.getLevel() >= MANAGER.getLevel();
    }
}
