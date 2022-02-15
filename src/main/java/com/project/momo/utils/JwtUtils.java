package com.project.momo.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class JwtUtils {

    public static final String REFRESH_TOKEN_HEADER = "refresh-token";
    public static final String MEMBER_ID_CLAIM_NAME = "memberId";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final int TOKEN_PREFIX_SUBSTRING_VALUE = 7;

    public static String resolveAccessToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX_SUBSTRING_VALUE);
        }

        return null;
    }

    public static String resolveRefreshToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX_SUBSTRING_VALUE);
        }

        return null;
    }

}
