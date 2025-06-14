package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurante restaurante, String correo, String token) ;
    Long idPropietario(String correo, String token, Restaurante restaurante);

    PageResponse<Restaurante> findAllRestaurantes(int page, int pageSize);
}
