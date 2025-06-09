package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {

    private final ICategoriaServicePort categoriaService;
    private final IRestauranteServicePort restauranteService;
    private final IPlatoServicePort platoServicePort;
    private final PlatoRequestMapper platoRequestMapper;

    @Override
    public void savePlato(PlatoDTO platoDTO, Long idPropietario) {
        Plato plato = platoRequestMapper.toPlato(platoDTO);
        plato.setRestaurante(restauranteService.findByIdAndIdPropietario(platoDTO.getIdRestaurante(), idPropietario));
        plato.setCategoria(categoriaService.findCategoriaByIdCategoria(platoDTO.getIdCategoria()));
        platoServicePort.savePlato(plato);


    }

    @Override
    public void updatePlato(PlatoDTOUpdate platoDTO, Long idPlato, Long idPropietario) {
        Plato plato = platoRequestMapper.toPlatoUpdate(platoDTO);
        platoServicePort.updatePlato(plato, idPlato, idPropietario);
    }
}
