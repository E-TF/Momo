package com.project.momo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.project.momo.dto.error.*;
import com.project.momo.exception.ResponseAlreadyCommittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JsonConverter {

    private final ObjectMapper objectMapper;

    public String convert(Exception exception) throws JsonProcessingException {

        ExceptionResponseDto exceptionResponseDto;

        if (exception instanceof PropertyBindingException) {

            exceptionResponseDto = new PropertyBindingExceptionResponseDto(((PropertyBindingException) exception).getPropertyName());

        } else if (exception instanceof JsonProcessingException) {

            exceptionResponseDto = JsonProcessingExceptionResponseDto.getInstance();

        } else if (exception instanceof MethodArgumentNotValidException) {

            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            exceptionResponseDto = new MethodArgumentNotValidExceptionResponseDto(errors);

        } else if (exception instanceof ResponseAlreadyCommittedException) {

            exceptionResponseDto = new ResponseAlreadyCommittedExceptionResponseDto(exception.getMessage(), ((ResponseAlreadyCommittedException) exception).getUnhandledException());

        } else if (exception instanceof InsufficientAuthenticationException) {

            exceptionResponseDto = InsufficientAuthenticationExceptionResponseDto.getInstance();

        } else {

            exceptionResponseDto = new ExceptionResponseDto(exception.getMessage());

        }

        return objectMapper.writeValueAsString(exceptionResponseDto);
    }
}
