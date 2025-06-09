package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;


    @Override
    public void savePlato(Plato plato) {
        validatePlato(plato);
        platoPersistencePort.savePlato(plato);
    }

    @Override
    public void updatePlato(Plato plato, Long id, Long idPropietario) {

       Plato platoExistente = findByIdAndIdRPropietario(id, idPropietario);

        if (plato.getDescripcion() != null) {
            platoExistente.setDescripcion(plato.getDescripcion());
        }
        if (plato.getPrecio() != null) {
            platoExistente.setPrecio(plato.getPrecio());
        }

        platoPersistencePort.savePlato(platoExistente);

    }

    @Override
    public void updatePlatoDisable(Long id, Boolean activo, Long idPropietario) {
        Plato platoExistente = findByIdAndIdRPropietario(id, idPropietario);
        platoExistente.setActivo(activo);
        platoPersistencePort.savePlato(platoExistente);
    }

    @Override
    public Plato findByIdAndIdRPropietario(Long id, Long idProietario) {
        Plato plato = platoPersistencePort.findByIdAndIdPropietario(id, idProietario);
        if (plato == null)
            throw new PlatoValidationException(ID_PLATO_NULL.getMessage());
        return plato;
    }

    private void validatePlato(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().isBlank()) {
            throw new PlatoValidationException(NAME_PLATO_EXCEPTION.getMessage());
        }

        if (plato.getPrecio() == null){
            throw new PlatoValidationException(PRICE_PLATO_NULL_EXCEPTION.getMessage());
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
