package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;

public interface IRestauranteHandler {

    void saveRestaurante(RestauranteDTO restauranteDTO, String token);
    PageResponseDTO<RestauranteDTOPage> findAllRestaurantes(int page, int pageSize);
}
