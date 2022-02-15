package com.project.momo.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class LoginIdNotFoundException extends InternalAuthenticationServiceException {

    public LoginIdNotFoundException() {
        super("존재하지 않는 아이디이거나 아이디를 잘못 입력했습니다.");
    }
}
