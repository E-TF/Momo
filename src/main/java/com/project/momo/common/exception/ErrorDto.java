package com.project.momo.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorDto {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;

    public ErrorDto(Exception exception) {
        this.message = exception.getMessage();
    }

    public ErrorDto(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

}
