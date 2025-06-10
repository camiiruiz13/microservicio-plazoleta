package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IPlatoPersistencePort {

    Plato savePlato(Plato plato);
    Plato findByIdAndIdPropietario(Long id, Long idPropietario);
    PageResponse<Plato> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize);
}
