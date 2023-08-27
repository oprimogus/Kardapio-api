package com.kardapio.kardapioapi.exceptions.apis;

public class ViaCEPException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Erro ao buscar o CEP informado";
    public ViaCEPException(String message) {
        super(message);
    }

    public ViaCEPException(String message, Throwable cause) {
        super(message, cause);
    }
}
