package com.kardapio.kardapioapi.exceptions.database;

public class RecordFailedException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Não foi possivel salvar o registro.";
    public RecordFailedException() {
        super(DEFAULT_MESSAGE);
    }

    public RecordFailedException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }
}