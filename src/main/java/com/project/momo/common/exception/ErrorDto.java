package com.project.momo.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorDto {

    private LocalDateTime timestamp;
    private String message;

    public ErrorDto(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorDto(Exception exception) {
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

}
