package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.config;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.RestauranteUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter.ApiAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl.GenericAplClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final RestauranteRepository repository;
    private final IRestauranteEntityMapper entityMapper;

    @Bean
    IRestaurantePersitencePort restaurantePersitencePort() {
        return new RestauranteJpaAdapter(repository, entityMapper);
    }

    @Bean
    IRestauranteServicePort restauranteServicePort(IApiClientPort apiClientPort) {
        return new RestauranteUseCase(apiClientPort, restaurantePersitencePort());
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    IGenericApiClient genericApiClient(RestTemplate restTemplate) {
        return new GenericAplClient(restTemplate);
    }

    @Bean
    IApiClientPort iApiClientPort(IGenericApiClient genericApiClient){
        return new ApiAdapter(genericApiClient);
    }
}
