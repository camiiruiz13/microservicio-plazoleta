package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception;

public class ErrorClientExeption  extends RuntimeException {

    public ErrorClientExeption(String message) {
        super(message);
    }

    public ErrorClientExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
