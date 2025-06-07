package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESTAURANT_SUCCES("Se ha creado el restaurante exitosamente");

    private final String message;
}
