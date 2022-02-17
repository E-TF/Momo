package com.project.momo.exception;

import lombok.Getter;

@Getter
public class ResponseAlreadyCommittedException extends RuntimeException {

    private Exception unhandledException;

    public ResponseAlreadyCommittedException(Exception exception) {
        super("Did not write to response since already committed");
        this.unhandledException = exception;
    }
}
