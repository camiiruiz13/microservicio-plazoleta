package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorException {

    HTTP_ERROR("Error HTTP: " ),
    ACCES_EXCEPTION("No se pudo acceder al recurso: "),
    REST_CLIENT_EXCEPTION("Error en la solicitud: ");

    private final String message;


}
