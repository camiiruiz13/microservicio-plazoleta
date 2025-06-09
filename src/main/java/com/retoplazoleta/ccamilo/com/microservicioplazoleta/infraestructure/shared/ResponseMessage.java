package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESTAURANT_SUCCES("Se ha creado el restaurante exitosamente"),
    PLATO_SUCCES("Se ha creado el plato exitosamente"),
    PLATO_UPDATE_SUCCES("Se ha actualizado el plato exitosamente");

    private final String message;
}
