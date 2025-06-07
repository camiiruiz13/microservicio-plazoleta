package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersitencePort {

    private final RestauranteRepository repository;
    private  final IRestauranteEntityMapper restauranteEntityMapper;

    @Override
    public Restaurante saveRestaurante(Restaurante restaurante) {
        RestauranteEntity restauranteEntity = repository.save(restauranteEntityMapper.toRestauranteEntity(restaurante));
        return restauranteEntityMapper.toRestauranteModel(restauranteEntity);
    }
}
