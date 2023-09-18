package com.kardapio.kardapioapi.exceptions.database;

public class RecordInvalidException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Dados estão inválidos.";
    public RecordInvalidException() {
        super(DEFAULT_MESSAGE);
    }

    public RecordInvalidException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }
}
