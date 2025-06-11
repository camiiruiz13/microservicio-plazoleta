package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PlatoDTOResponse;

public interface IPlatoHandler {

    void savePlato(PlatoDTO platoDTO, Long idPropietario);
    void updatePlato(PlatoDTOUpdate platoDTO, Long idPlato, Long idPropietario);
    void updatePlatoDisable(Long id, Boolean activo, Long idPropietario);
    PageResponseDTO<PlatoDTOResponse> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize);

}
