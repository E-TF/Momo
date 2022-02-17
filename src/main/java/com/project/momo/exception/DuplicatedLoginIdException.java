package com.project.momo.exception;

public class DuplicatedLoginIdException extends RuntimeException {

    private static final DuplicatedLoginIdException INSTANCE = new DuplicatedLoginIdException();

    private DuplicatedLoginIdException() {
        super("이미 사용중인 아이디입니다.");
    }

    public static DuplicatedLoginIdException getInstance() {
        return INSTANCE;
    }
}
