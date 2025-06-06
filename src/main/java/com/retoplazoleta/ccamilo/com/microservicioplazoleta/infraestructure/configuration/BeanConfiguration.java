package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.configuration;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final RestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;

    IRestaurantePersitencePort restaurantePersitencePort(){
        return new RestauranteJpaAdapter(restauranteEntityMapper,restauranteRepository);
    }
}
