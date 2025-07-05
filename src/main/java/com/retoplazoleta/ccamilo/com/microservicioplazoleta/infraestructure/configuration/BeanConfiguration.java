package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.*;
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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
    RestTemplate restTemplate(ObjectMapper baseMapper) {
        ObjectMapper mapper = baseMapper.copy()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter(mapper);

        return new RestTemplateBuilder()
                .additionalMessageConverters(jsonConverter)
                .build();


    }

    @Bean
    IGenericApiClient genericApiClient(ObjectMapper objectMapper) {
        return new GenericAplClient(restTemplate(objectMapper),objectMapper);
    }

    @Bean
    IApiClientPort iApiClientPort(IGenericApiClient genericApiClient, ObjectMapper objectMapper){
        return new ApiAdapter(genericApiClient, objectMapper);
    }

    @Bean
    ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(categoriaEntityMapper,categoriaRepository);
    }



    @Bean
    IPlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(platoEntityMapper,platoRepository);
    }

    @Bean
    IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(categoriaPersistencePort(), platoPersistencePort(), restaurantePersitencePort());
    }

    @Bean
    IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter( pedidoRepository, pedidoEntityMapper, pedidoPlatoRepository, pedidoPlatoEntityMapper, platoEntityMapper);

    }

    @Bean
    IPedidoServicePort pedidoServicePort(IApiClientPort apiClientPort){
        return new PedidoUseCase(apiClientPort,pedidoPersistencePort(), platoPersistencePort());
    }





}
