package com.project.momo.exception;

public class DuplicatedLoginIdException extends RuntimeException {
    public DuplicatedLoginIdException() {
        super("이미 사용중인 아이디입니다.");
    }
}
