package com.project.momo.common.exception.s3;

public class DuplicatedFileNameException extends RuntimeException {

    public  DuplicatedFileNameException(String fileName) {
        super("File name : " + fileName + " already exists and failed to create file. Change file name or try again later.");
    }
}
