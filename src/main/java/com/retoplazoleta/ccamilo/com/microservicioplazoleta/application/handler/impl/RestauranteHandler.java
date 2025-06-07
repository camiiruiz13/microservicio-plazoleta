package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IRestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.RestauranteRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteHandler implements IRestauranteHandler {


    private final RestauranteRequestMapper restauranteRequestMapper;
    private final IRestauranteServicePort restauranteServicePort;

    @Override
    public void saveRestaurante(RestauranteDTO restauranteDTO, String token) {
        Restaurante restaurante = restauranteRequestMapper.toRestaurante(restauranteDTO);
        restaurante.setId(restauranteServicePort.idPropietario(restauranteDTO.getCorreo(), token));
        restauranteServicePort.saveRestaurante(restaurante);
    }
}
