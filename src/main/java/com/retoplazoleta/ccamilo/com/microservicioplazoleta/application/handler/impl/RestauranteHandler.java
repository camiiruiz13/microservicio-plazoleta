package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IRestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IRestauranteResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PageResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.RestauranteRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteHandler implements IRestauranteHandler {


    private final RestauranteRequestMapper restauranteRequestMapper;
    private final IRestauranteResponseMapper restauranteResponseMapper;
    private final IRestauranteServicePort restauranteServicePort;

    @Override
    public void saveRestaurante(RestauranteDTO restauranteDTO, String token) {
        Restaurante restaurante = restauranteRequestMapper.toRestaurante(restauranteDTO);
        restauranteServicePort.saveRestaurante(restaurante, restauranteDTO.getCorreo(), token);
    }

    @Override
    public PageResponseDTO<RestauranteDTOPage> findAllRestaurantes(int page, int pageSize) {

        return PageResponseMapper.toResponseDTO(
                restauranteServicePort.findAllRestaurantes(page, pageSize),
                restauranteResponseMapper::toResponseRestauranteList
        );
    }
}
