package com.project.momo.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class InvalidArgsException extends RuntimeException {

    public InvalidArgsException(MethodArgumentNotValidException exception) {
        super(exception.getBindingResult().getFieldErrors()
                .stream().map(f -> f.getField() + " : " + f.getDefaultMessage())
                .collect(Collectors.joining(" / ")));
    }
}
