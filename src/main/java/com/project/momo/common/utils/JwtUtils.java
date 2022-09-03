package com.project.momo.common.utils;

import com.project.momo.common.exception.auth.JwtNotFoundException;
import com.project.momo.security.consts.TokenType;
import com.project.momo.common.exception.auth.JwtException;
import com.project.momo.security.consts.JwtConst;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class JwtUtils {

    public static String resolveToken(HttpServletRequest request, TokenType tokenType) throws JwtException {
        String bearerToken = request.getHeader(tokenType.getTokenHeader());

        if (bearerToken == null || !bearerToken.startsWith(JwtConst.TOKEN_PREFIX))
            throw new JwtException("토큰의 인증 타입(Bearer)이 올바르지 않습니다.");
        if (StringUtils.hasText(bearerToken))
            return bearerToken.substring(JwtConst.TOKEN_PREFIX_SUBSTRING_VALUE);

        throw JwtNotFoundException.getInstance();
    }

    public static boolean hasToken(HttpServletRequest request, TokenType tokenType) {
        return StringUtils.hasText(request.getHeader(tokenType.getTokenHeader()));
    }
}
