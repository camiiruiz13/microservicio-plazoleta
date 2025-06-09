package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IRestaurantePersitencePort {

    Restaurante saveRestaurante(Restaurante restaurante);
    Restaurante findByIdAndIdPropietario(Long idRestaurante, Long idPropietario);
    PageResponse<Restaurante> findAllRestaurantes(int page, int pageSize);


}
