package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.*;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.CategoriaUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PedidoUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PlatoUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.RestauranteUseCase;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter.ApiAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl.GenericAplClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.CategoriaJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PedidoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.*;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.*;
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
    private final PedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;

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

    @Bean
    IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter( pedidoRepository, pedidoEntityMapper, pedidoPlatoRepository, pedidoPlatoEntityMapper, platoRepository);

    }

    @Bean
    IPedidoServicePort pedidoServicePort(){
        return new PedidoUseCase(pedidoPersistencePort());
    }





}
