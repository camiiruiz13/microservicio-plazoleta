package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.CategoriaUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PlatoUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.RestauranteUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter.ApiAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl.GenericAplClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.CategoriaJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.CategoriaRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private  final ICategoriaEntityMapper categoriaEntityMapper;
    private final CategoriaRepository categoriaRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final PlatoRepository platoRepository;
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
    IGenericApiClient genericApiClient(ObjectMapper objectMapper) {
        return new GenericAplClient(restTemplate(),objectMapper);
    }

    @Bean
    IApiClientPort iApiClientPort(IGenericApiClient genericApiClient){
        return new ApiAdapter(genericApiClient);
    }

    @Bean
    ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(categoriaEntityMapper,categoriaRepository);
    }

    @Bean
    ICategoriaServicePort categoriaServicePort(){
        return new CategoriaUseCase(categoriaPersistencePort());
    }

    @Bean
    IPlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(platoEntityMapper,platoRepository);
    }

    @Bean
    IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(platoPersistencePort());
    }



}
