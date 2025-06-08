package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.CategoriaValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.ERROR_CATEGORIA;

@RequiredArgsConstructor
public class CategoriaUseCase implements ICategoriaServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;

    @Override
    public Categoria findCategoriaBy(Long id) {
        Categoria categoria = categoriaPersistencePort.findByIdCategoria(id);
        if (categoria == null)
            throw new CategoriaValidationException(ERROR_CATEGORIA.getMessage());
        return categoria;
    }
}




