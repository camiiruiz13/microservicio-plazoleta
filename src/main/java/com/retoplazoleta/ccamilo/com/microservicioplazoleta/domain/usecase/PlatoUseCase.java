package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.CategoriaValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PlatoUseCase implements IPlatoServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersitencePort restaurantePersitencePort;


    @Override
    public void savePlato(Plato plato) {
        validatePlato(plato);
        plato.setCategoria(findCategoriaByIdCategoria(plato.getCategoria().getId()));
        plato.setRestaurante(findByIdAndIdPropietario(plato.getRestaurante().getId(), plato.getRestaurante().getIdPropietario()));
        platoPersistencePort.savePlato(plato);
    }

    @Override
    public Categoria findCategoriaByIdCategoria(Long id) {
        if (id == null)
            throw new CategoriaValidationException(ID_CATEGORIA_NULL.getMessage());
        Categoria categoria = categoriaPersistencePort.findByIdCategoria(id);
        if (categoria == null)
            throw new CategoriaValidationException(ERROR_CATEGORIA.getMessage());
        return categoria;
    }

    @Override
    public Restaurante findByIdAndIdPropietario(Long idRestaurante, Long idPropietario) {
        if (idRestaurante == null)
            throw new RestauranteValidationException(ID_RESTAURANTE_NULL.getMessage());
        Restaurante restaurante = restaurantePersitencePort.findByIdAndIdPropietario(idRestaurante, idPropietario);
        if (restaurante == null)
            throw new RestauranteValidationException(ERROR_USER.getMessage());
        return restaurante;
    }


    @Override
    public void updatePlato(Plato plato, Long id, Long idPropietario) {

        Plato platoExistente = findByIdAndIdRPropietario(id, idPropietario);

        if (plato.getDescripcion() != null) {
            platoExistente.setDescripcion(plato.getDescripcion());
        }
        if (plato.getPrecio() != null) {

            if (plato.getPrecio() <= 0) {
                throw new PlatoValidationException(PRICE_PLATO_EXCEPTION.getMessage());
            }

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

    @Override
    public PageResponse<Plato> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize) {
        return platoPersistencePort.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, pageSize);
    }

    private void validatePlato(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().isBlank()) {
            throw new PlatoValidationException(NAME_PLATO_EXCEPTION.getMessage());
        }

        if (plato.getPrecio() == null) {
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
