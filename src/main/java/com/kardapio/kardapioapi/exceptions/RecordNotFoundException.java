package com.kardapio.kardapioapi.exceptions;


public class RecordNotFoundException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Registro não encontrado";
    public RecordNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public RecordNotFoundException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }
}
