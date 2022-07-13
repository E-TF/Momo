package com.project.momo.common.utils;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    public static long getMemberId() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        return (long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
