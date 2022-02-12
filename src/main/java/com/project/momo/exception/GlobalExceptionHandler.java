package com.project.momo.exception;

import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.ResponseManager;
import org.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

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
        JSONObject jsonObject = JsonConverter.mapToJson(errors);

        ResponseManager.sendError(response, jsonObject, HttpServletResponse.SC_BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void handleNoHandlerFoundException(HttpServletResponse response) throws IOException {
        JSONObject jsonObject = JsonConverter.stringToJson("page not found");
        ResponseManager.sendError(response, jsonObject, HttpServletResponse.SC_NOT_FOUND);
    }
}
