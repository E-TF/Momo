package com.project.momo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "올바르지 않은 JSON 형식 입니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 토큰입니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다."),
    INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST, "아이디는 크기가 3에서 45 사이여야 합니다"),

    //MyPage, MemberService
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    EXCEED_PAYMENT_CNT_LIMIT(HttpStatus.BAD_REQUEST, "결제 수단은 최대 3개까지 등록이 가능합니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    DUPLICATED_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일힙니다."),
    NO_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다."),
    NO_PAYMENT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 결제수단입니다."),

    //CategoryService
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    DUPLICATED_CATEGORY_NAME(HttpStatus.CONFLICT, "이미 존재하는 카테고리명입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
