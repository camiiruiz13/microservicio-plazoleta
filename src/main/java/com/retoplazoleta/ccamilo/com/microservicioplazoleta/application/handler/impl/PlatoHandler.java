package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PlatoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PageResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {


    private final IPlatoServicePort platoServicePort;
    private final PlatoRequestMapper platoRequestMapper;
    private final PlatoResponseMapper platoResponseMapper;

    @Override
    public void savePlato(PlatoDTO platoDTO) {
        Plato plato = platoRequestMapper.toPlato(platoDTO);
        platoServicePort.savePlato(plato);
    }

    @Override
    public void updatePlato(PlatoDTOUpdate platoDTO, Long idPlato, Long idPropietario) {
        Plato plato = platoRequestMapper.toPlatoUpdate(platoDTO);
        platoServicePort.updatePlato(plato, idPlato, idPropietario);
    }

    @Override
    public void updatePlatoDisable(Long id, Boolean activo, Long idPropietario) {
        platoServicePort.updatePlatoDisable(id, activo, idPropietario);
    }

    @Override
    public PageResponseDTO<PlatoDTOResponse> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize) {
        return PageResponseMapper.toResponseDTO(
                platoServicePort.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, pageSize),
                platoResponseMapper::toPlatoDTo
        );
    }
}
