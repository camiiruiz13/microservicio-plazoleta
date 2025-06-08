package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;

public interface ICategoriaServicePort {
    Categoria findCategoriaBy(Long id);
}
