package com.project.momo.common.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.common.exception.ErrorDto;
import com.project.momo.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ResponseError {

    private final ObjectMapper objectMapper;

    public ResponseEntity<ErrorDto> of(ErrorCode code) {
        return ResponseEntity.status(code.getHttpStatus()).body(new ErrorDto(code.getMessage()));
    }

    public ResponseEntity<ErrorDto> from(RuntimeException exception, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new ErrorDto(exception.getMessage()));
    }

    public String toJson(Exception exception) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new ErrorDto(exception));
    }
}
