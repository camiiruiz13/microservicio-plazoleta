package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception;

public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException(String message) {
        super(message);
    }
}
