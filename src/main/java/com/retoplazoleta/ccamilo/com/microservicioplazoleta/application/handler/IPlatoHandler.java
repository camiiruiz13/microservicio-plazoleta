package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;

public interface IPlatoHandler {

    void savePlato(PlatoDTO platoDTO, Long idPropietario);
    void updatePlato(PlatoDTOUpdate platoDTO, Long idPlato, Long idPropietario);
    void updatePlatoDisable(Long id, Boolean activo, Long idPropietario);
}
