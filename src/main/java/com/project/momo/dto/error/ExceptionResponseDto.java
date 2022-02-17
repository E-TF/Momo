package com.project.momo.dto.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponseDto {

    private LocalDateTime timestamp;
    private String message;

    public ExceptionResponseDto(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
