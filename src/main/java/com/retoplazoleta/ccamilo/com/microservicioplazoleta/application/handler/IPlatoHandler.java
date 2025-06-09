package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;

public interface IPlatoHandler {

    void savePlato(PlatoDTO platoDTO,Long idPropietario);
}
