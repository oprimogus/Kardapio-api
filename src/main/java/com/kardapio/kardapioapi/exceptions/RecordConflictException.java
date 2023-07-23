package com.kardapio.kardapioapi.exceptions;

public class RecordConflictException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Registro ja existe";
    public RecordConflictException() {
        super(DEFAULT_MESSAGE);
    }

    public RecordConflictException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }
}
