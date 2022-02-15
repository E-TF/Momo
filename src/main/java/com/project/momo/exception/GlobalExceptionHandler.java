package com.project.momo.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.ResponseManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException exception) throws IOException {
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        String json = JsonConverter.mapToJson(errors);

        ResponseManager.sendError(response, json, HttpServletResponse.SC_BAD_REQUEST);
    }

}
