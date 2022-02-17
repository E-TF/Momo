package com.project.momo.dto.error;

public class ResponseAlreadyCommittedExceptionResponseDto extends ExceptionResponseDto {

    private String unhandledException;

    public ResponseAlreadyCommittedExceptionResponseDto(String message, Exception exception) {
        super(message);
        this.unhandledException = exception.getClass().toString();
    }
}
