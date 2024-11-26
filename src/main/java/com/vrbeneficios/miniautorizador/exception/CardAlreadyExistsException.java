package com.vrbeneficios.miniautorizador.exception;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String message) {
        super(message);
    }
}
