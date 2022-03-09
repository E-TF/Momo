package com.project.momo.security.role;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Role {

    private static final List<GrantedAuthority> USER;
    private static final List<GrantedAuthority> TEMP;

    static {
        USER = new ArrayList<>();
        USER.add(new SimpleGrantedAuthority("ROLE_USER"));
        TEMP = new ArrayList<>();
        TEMP.add(new SimpleGrantedAuthority("ROLE_TEMPORARY"));
    }

    public static List<GrantedAuthority> getUser() {
        return USER;
    }

    public static List<GrantedAuthority> getTemp() {
        return TEMP;
    }
}
