package com.project.momo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "올바르지 않은 JSON 형식 입니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 토큰입니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다."),
    INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST, "아이디는 크기가 3에서 45 사이여야 합니다");


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
