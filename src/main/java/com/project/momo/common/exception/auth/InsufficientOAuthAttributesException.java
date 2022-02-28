package com.project.momo.common.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class InsufficientOAuthAttributesException extends AuthenticationException {

    private static final InsufficientOAuthAttributesException INSTANCE = new InsufficientOAuthAttributesException();

    private InsufficientOAuthAttributesException() {
        super("OAuth에서 제공되는 정보가 충분하지 않습니다.");
    }

    public static InsufficientOAuthAttributesException getInstance() {
        return INSTANCE;
    }
}

