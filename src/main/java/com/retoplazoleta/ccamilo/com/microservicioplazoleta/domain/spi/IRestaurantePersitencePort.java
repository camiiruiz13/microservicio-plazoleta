package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;

public interface IRestaurantePersitencePort {

    Restaurante saveRestaurante(Restaurante restaurante);


}
