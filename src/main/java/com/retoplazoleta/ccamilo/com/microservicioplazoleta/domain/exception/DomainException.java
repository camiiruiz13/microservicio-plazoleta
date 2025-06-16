package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
