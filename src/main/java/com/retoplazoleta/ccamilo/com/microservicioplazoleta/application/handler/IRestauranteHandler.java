package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;

public interface IRestauranteHandler {

    void saveRestaurante(RestauranteDTO restauranteDTO, String token);
}
