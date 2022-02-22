package com.project.momo.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(MethodArgumentNotValidException exception) {
        super(exception.getBindingResult().getFieldErrors()
                .stream().map(f -> f.getField() + " : " + f.getDefaultMessage())
                .collect(Collectors.joining(" / ")));
    }

}
