package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurante restaurante);
    Long idPropietario(String correo, String token, Restaurante restaurante);
    Restaurante findByIdAndIdPropietario(Long idRestaurante, Long idPropietario);
    PageResponse<Restaurante> findAllRestaurantes(int page, int pageSize);
}
