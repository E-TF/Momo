package com.project.momo.dto.error;

public class JsonProcessingExceptionResponseDto extends ExceptionResponseDto {
    private static final JsonProcessingExceptionResponseDto INSTANCE = new JsonProcessingExceptionResponseDto();

    protected JsonProcessingExceptionResponseDto() {
        super("올바르지 않은 Json 포맷입니다.");
    }

    public static JsonProcessingExceptionResponseDto getInstance() {
        return INSTANCE;
    }
}
