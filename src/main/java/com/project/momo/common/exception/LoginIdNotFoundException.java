package com.project.momo.common.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class LoginIdNotFoundException extends InternalAuthenticationServiceException {

    private static final LoginIdNotFoundException INSTANCE = new LoginIdNotFoundException();

    private LoginIdNotFoundException() {
        super("존재하지 않는 아이디이거나 아이디를 잘못 입력했습니다.");
    }

    public static LoginIdNotFoundException getInstance() {
        return INSTANCE;
    }
}
