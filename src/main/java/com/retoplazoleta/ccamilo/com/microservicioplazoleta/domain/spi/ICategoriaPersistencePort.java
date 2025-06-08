package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;

public interface ICategoriaPersistencePort {

    Categoria findByIdCategoria(Long idCategoria);
}
