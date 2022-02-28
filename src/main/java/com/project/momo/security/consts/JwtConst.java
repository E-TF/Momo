package com.project.momo.security.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConst {

    public static final String REFRESH_TOKEN_HEADER = "refresh-token";

    public static final String MEMBER_ID_CLAIM_NAME = "memberId";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final int TOKEN_PREFIX_SUBSTRING_VALUE = 7;

}
