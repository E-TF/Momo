package com.project.momo.common.exception.auth;

public class InvalidOauthTypeException extends RuntimeException {

    public InvalidOauthTypeException(String registrationIdInput) {
        super(registrationIdInput + " 은/는 애플리케이션에서 제공되는 registrationId가 아닙니다.");
    }
}
