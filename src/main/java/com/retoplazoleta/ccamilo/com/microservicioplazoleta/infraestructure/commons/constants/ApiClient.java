package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiClient {

    FIND_BY_CORREO_API("/buscarPorCorreo/{correo}"),
    FIND_BY_ID_API("/buscarPorId/{id}"),
    SEND_NOTIFICATION_ID_USER("/enviar-notificacion/{destinatario}/pedido/{mensaje}"),
    CREATE_TRACE("/crearTrazabilidad");

    private final String message;
}
