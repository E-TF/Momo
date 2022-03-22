package com.project.momo.common.utils;

import com.project.momo.security.role.Role;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@UtilityClass
public class AuthenticationTokenFactory {

    private static UsernamePasswordAuthenticationToken TEMP_TOKEN = new UsernamePasswordAuthenticationToken(null, null, Role.getDefaultTempRole());

    public static UsernamePasswordAuthenticationToken tempToken() {
        return TEMP_TOKEN;
    }

    public static UsernamePasswordAuthenticationToken userToken(long memberId) {
        return new UsernamePasswordAuthenticationToken(memberId, null, Role.getDefaultUserRole());
    }

}
