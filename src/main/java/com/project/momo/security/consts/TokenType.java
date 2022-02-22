package com.project.momo.security.consts;

import org.springframework.http.HttpHeaders;

public enum TokenType {

    ACCESS(HttpHeaders.AUTHORIZATION),
    REFRESH(JwtConst.REFRESH_TOKEN_HEADER);

    private final String tokenHeader;

    TokenType(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

}
