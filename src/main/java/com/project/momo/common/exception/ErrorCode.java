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
    DUPLICATED_CATEGORY_NAME(HttpStatus.CONFLICT, "이미 존재하는 카테고리명입니다."),

    //RegionService
    CITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 CITY 번호입니다."),
    STATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 STATE 번호입니다."),
    DISTRICT_NOT_FOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 DISTRICT 번호입니다."),

    //ClubService
    CLUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 모임입니다."),
    DUPLICATED_CLUB_NAME(HttpStatus.CONFLICT, "이미 존재하는 모임 이름입니다."),
    EXCEED_CLUB_SIZE_LIMIT(HttpStatus.BAD_REQUEST, "최대 가입 인원을 초과하였습니다."),
    DUPLICATED_CLUB_JOIN(HttpStatus.CONFLICT, "이미 가입한 모임입니다."),
    EXCEED_CLUB_CREATION_LIMIT_PER_MEMBER(HttpStatus.BAD_REQUEST, "최대 모임 생성 개수를 초과하였습니다."),

    //DistributedLock
    NO_LOCK_NAME_SET(HttpStatus.INTERNAL_SERVER_ERROR, "LOCK NAME 이 설정되지 않았습니다."),
    CONNECTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "커넥션을 찾을 수 없습니다."),
    NO_RESULT_SET(HttpStatus.INTERNAL_SERVER_ERROR, "LOCK 수행 결과값이 없습니다."),
    LOCK_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "LOCK 수행에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
