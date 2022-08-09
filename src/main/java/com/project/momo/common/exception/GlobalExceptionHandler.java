package com.project.momo.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.momo.common.exception.s3.DuplicatedFileNameException;
import com.project.momo.common.exception.s3.S3MultipartConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(new InvalidArgsException(exception)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDto> handleJsonProcessingException(JsonProcessingException exception) {
        ErrorCode code = ErrorCode.INVALID_JSON_FORMAT;
        return ResponseEntity.status(code.getHttpStatus()).body(new ErrorDto(code));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> handleBusinessException(BusinessException exception) {
        ErrorCode code = exception.getErrorCode();
        return ResponseEntity.status(code.getHttpStatus()).body(new ErrorDto(code));
    }

    @ExceptionHandler(S3MultipartConversionException.class)
    public ResponseEntity<ErrorDto> handleS3MultipartConversionException(S3MultipartConversionException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(exception));
    }

    @ExceptionHandler(DuplicatedFileNameException.class)
    public ResponseEntity<ErrorDto> handleDuplicatedFileNameException(DuplicatedFileNameException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(exception));
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorDto> handleDistributedLockException(DistributedLockException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(exception));
    }
}
