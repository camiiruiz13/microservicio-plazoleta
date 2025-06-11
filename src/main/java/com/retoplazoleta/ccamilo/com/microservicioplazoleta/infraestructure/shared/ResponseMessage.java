package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESTAURANT_SUCCES("Se ha creado el restaurante exitosamente"),
    RESTAURANT_LIST("Se listan los restaurantes"),
    PLATO_LIST("Se listan los platos correctamente"),
    PLATO_SUCCES("Se ha creado el plato exitosamente"),
    PLATO_UPDATE_SUCCES("Se ha actualizado el plato exitosamente"),
    PLATO("Plato"),
    HABILITADO("habilitado"),
    DESHABILITADO("deshabilitado"),
    SUCCES_DISABLE(" correctamente.");

    private final String message;
}
