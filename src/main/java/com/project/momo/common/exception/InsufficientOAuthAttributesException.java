package com.project.momo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class InsufficientOAuthAttributesException extends AuthenticationException {

    private final HttpStatus httpStatus = HttpStatus.LOCKED;

    private static final InsufficientOAuthAttributesException INSTANCE = new InsufficientOAuthAttributesException();

    private InsufficientOAuthAttributesException() {
        super("OAuth에서 제공되는 정보가 충분하지 않습니다.");
    }

    public static InsufficientOAuthAttributesException getInstance() {
        return INSTANCE;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}

