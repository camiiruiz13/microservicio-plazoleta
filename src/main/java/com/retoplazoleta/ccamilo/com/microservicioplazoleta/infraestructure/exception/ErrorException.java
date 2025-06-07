package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorException {

    HTTP_ERROR("Error HTTP: " ),
    ACCES_EXCEPTION("No se pudo acceder al recurso: "),
    REST_CLIENT_EXCEPTION("Error en la solicitud: "),
    TOKEN_VENCIDO("El token ha vencido"),
    TOKEN_INVALID("Token inválido: " ),
    RESTAURANT_VALIDATION("Error interno en el servidor: " ),
    GENERIC_EXCEPTION("Error interno en el servidor: " ),
    RESTAURANTE_ROLE_EXCEPTION("Rol incorrecto para crear retaurante " ),
    ACCES_DENIED("Acceso denegado: No tiene permisos para realizar esta operación"),;

    private final String message;


}
