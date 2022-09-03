package com.project.momo.common.exception.auth;

public class JwtNotFoundException extends JwtException {
    private static final JwtNotFoundException INSTANCE = new JwtNotFoundException();

    private JwtNotFoundException() {
        super("토큰이 입력되지 않았습니다.");
    }

    public static JwtNotFoundException getInstance() {
        return INSTANCE;
    }
}
