package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurante restaurante);
    Long idPropietario(String correo, String token);

}
