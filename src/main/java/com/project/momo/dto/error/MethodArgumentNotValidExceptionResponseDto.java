package com.project.momo.dto.error;

import java.util.Map;


public class MethodArgumentNotValidExceptionResponseDto extends ExceptionResponseDto {

    private Map<String, String> errors;

    public MethodArgumentNotValidExceptionResponseDto(Map<String, String> errors) {
        super("입력 매개변수가 유효하지 않습니다.");
        this.errors = errors;
    }
}
