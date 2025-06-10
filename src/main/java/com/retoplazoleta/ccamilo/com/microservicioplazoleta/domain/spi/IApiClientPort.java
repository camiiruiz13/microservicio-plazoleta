package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

public interface IApiClientPort {

    Long idPropietario(String correo,  String token);
    Long idPropietario(String correo,  String token, Object model);
}
