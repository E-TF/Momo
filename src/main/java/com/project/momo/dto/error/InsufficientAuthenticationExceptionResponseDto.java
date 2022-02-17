package com.project.momo.dto.error;

public class InsufficientAuthenticationExceptionResponseDto extends ExceptionResponseDto{

    private static final InsufficientAuthenticationExceptionResponseDto INSTANCE = new InsufficientAuthenticationExceptionResponseDto();

    private InsufficientAuthenticationExceptionResponseDto() {
        super("인증 정보가 충분하지 않습니다. Access-Token 의 여부와 Authorization 헤더에 접두사가 올바르게 작성됐는지 확인하세요.");
    }

    public static InsufficientAuthenticationExceptionResponseDto getInstance() {
        return INSTANCE;
    }
}
