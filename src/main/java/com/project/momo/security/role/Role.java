package com.project.momo.security.role;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class Role {

    public static final String USER = "USER";
    public static final String TEMPORARY = "TEMPORARY";
    public static final String ADMIN = "ADMIN";

    private static final List<GrantedAuthority> USER_ROLE = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    private static final List<GrantedAuthority> TEMPORARY_ROLE = Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEMPORARY"));
    private static final List<GrantedAuthority> ADMIN_ROLE = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));


    public static List<GrantedAuthority> getDefaultUserRole() {
        return USER_ROLE;
    }

    public static List<GrantedAuthority> getDefaultTempRole() {
        return TEMPORARY_ROLE;
    }

    public static List<GrantedAuthority> getDefaultAdminRole() {
        return ADMIN_ROLE;
    }
}
