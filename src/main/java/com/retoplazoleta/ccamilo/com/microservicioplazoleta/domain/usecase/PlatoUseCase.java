package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;


    @Override
    public void savePlato(Plato plato, Long idPropietario) {
        validatePlato(plato);
        platoPersistencePort.savePlato(plato);
    }

    private void validatePlato(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().isBlank()) {
            throw new PlatoValidationException(NAME_PLATO_EXCEPTION.getMessage());
        }

        if (plato.getPrecio() <= 0) {
            throw new PlatoValidationException(PRICE_PLATO_EXCEPTION.getMessage());
        }

        if (plato.getDescripcion() == null || plato.getDescripcion().isBlank()) {
            throw new PlatoValidationException(DESCRIPTION_PLATO_EXCEPTION.getMessage());
        }

        if (plato.getUrlImagen() == null || plato.getUrlImagen().isBlank()) {
            throw new PlatoValidationException(URL_PLATO_EXCEPTION.getMessage());
        }
    }

}
