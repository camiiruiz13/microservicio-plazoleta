package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import lombok.RequiredArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class RestauranteUseCase implements IRestauranteServicePort {

    private final IApiClientPort apiClientPort;
    private final IRestaurantePersitencePort restaurantePersitencePort;

    @Override
    public void saveRestaurante(Restaurante restaurante) {
        validateRestaurante(restaurante);
        restaurantePersitencePort.saveRestaurante(restaurante);
    }

    @Override
    public Long idPropietario(String correo, String token) {
        return apiClientPort.idPropietario(correo, token);
    }

    private void validateRestaurante(Restaurante restaurante) {
        if (restaurante.getNombre() == null || restaurante.getNombre().isBlank()) {
            throw new RestauranteValidationException(REQUIRED_NAME.getMessage());
        }

        if (restaurante.getNombre().matches(MATCHES.getMessage())) {
            throw new RestauranteValidationException(NUMERIC_NAME.getMessage());
        }

        if (restaurante.getNit() == null || !restaurante.getNit().matches(MATCHES.getMessage())) {
            throw new RestauranteValidationException(NUMERIC_NIT.getMessage());
        }

        if (restaurante.getTelefono() == null ||
                !restaurante.getTelefono().matches(MATCHES_TEL.getMessage())) {
            throw new RestauranteValidationException(CARACTER_TEL.getMessage());
        }

    }
}
