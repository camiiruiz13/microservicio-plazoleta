package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiClient {

    FIND_BY_CORREO_API("/buscarPorCorreo/{correo}");

    private final String message;
}
