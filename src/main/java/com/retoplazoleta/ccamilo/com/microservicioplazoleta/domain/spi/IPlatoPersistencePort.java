package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;

public interface IPlatoPersistencePort {

    Plato savePlato(Plato plato);
    Plato findByIdAndIdPropietario(Long id, Long idPropietario);
}
