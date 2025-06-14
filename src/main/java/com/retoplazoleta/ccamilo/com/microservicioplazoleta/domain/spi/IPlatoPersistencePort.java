package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

import java.util.List;

public interface IPlatoPersistencePort {

    Plato savePlato(Plato plato);
    Plato findByIdAndIdPropietario(Long id, Long idPropietario);
    Plato findById(Long id);
    PageResponse<Plato> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize);
    boolean existsPlatosOfRestaurant(List<Long> idsPlatos, Long idRestaurante);


}
