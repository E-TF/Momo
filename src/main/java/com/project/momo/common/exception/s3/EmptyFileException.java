package com.project.momo.common.exception.s3;

public class EmptyFileException extends RuntimeException {
    private static EmptyFileException INSTANCE = new EmptyFileException("Given file is empty.");

    public EmptyFileException(String message) {
        super(message);
    }

    public static EmptyFileException getInstance() {
        return INSTANCE;
    }
}
