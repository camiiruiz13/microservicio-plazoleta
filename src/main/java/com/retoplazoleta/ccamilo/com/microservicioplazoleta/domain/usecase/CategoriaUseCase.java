package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.CategoriaValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.ERROR_CATEGORIA;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.ID_CATEGORIA_NULL;


@RequiredArgsConstructor
public class CategoriaUseCase implements ICategoriaServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;

    @Override
    public Categoria findCategoriaByIdCategoria(Long id) {
        if (id == null)
            throw new CategoriaValidationException(ID_CATEGORIA_NULL.getMessage());
        Categoria categoria = categoriaPersistencePort.findByIdCategoria(id);
        if (categoria == null)
            throw new CategoriaValidationException(ERROR_CATEGORIA.getMessage());
        return categoria;
    }
}




