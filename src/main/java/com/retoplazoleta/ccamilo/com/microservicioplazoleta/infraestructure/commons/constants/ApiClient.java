package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiClient {

    FIND_BY_CORREO_API("/buscarPorCorreo/{correo}"),
    FIND_BY_ID_API("/buscarPorId/{id}"),
    SEND_NOTIFICATION_ID_USER("/enviar-notificacion/{destinatario}/pedido/{mensaje}"),
    CREATE_TRACE("/crearTrazabilidad"),
    FIND_BY_IDS_API("/buscarPorIds");

    private final String message;
}
