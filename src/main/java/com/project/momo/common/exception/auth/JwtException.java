package com.project.momo.common.exception.auth;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
