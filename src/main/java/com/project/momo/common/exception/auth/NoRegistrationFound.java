package com.project.momo.common.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class NoRegistrationFound extends AuthenticationException {

    private static final NoRegistrationFound INSTANCE = new NoRegistrationFound();

    private NoRegistrationFound() {
        super("적절한 OAuth Registration을 찾지 못했습니다.");
    }

    public static NoRegistrationFound getInstance() {
        return INSTANCE;
    }
}

