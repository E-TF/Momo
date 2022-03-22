package com.project.momo.common.exception.s3;

public class S3MultipartConversionException extends RuntimeException {

    private static final S3MultipartConversionException INSTANCE = new S3MultipartConversionException();

    private S3MultipartConversionException() {
        super("Failed to convert MultipartFile to File.");
    }

    public static S3MultipartConversionException getInstance() {
        return INSTANCE;
    }
}
