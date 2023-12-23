package com.kardapio.kardapioapi.exceptions.apis;

public class RequestException extends RuntimeException {

    static final String DEFAULT_MESSAGE = "Erro na requisição ao serviço solicitado";
    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
