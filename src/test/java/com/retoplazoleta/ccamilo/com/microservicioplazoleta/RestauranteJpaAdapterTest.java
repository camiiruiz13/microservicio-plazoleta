package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class RestauranteJpaAdapterTest {

    @Mock
    private IRestauranteEntityMapper restauranteEntityMapper;

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private RestauranteJpaAdapter adapter;

    @Test
    @Order(1)
    void saveRestaurante_ShouldMapAndPersistAndReturnRestaurante() {

        Restaurante inputRestaurante = new Restaurante();
        RestauranteEntity entityToSave = new RestauranteEntity();
        RestauranteEntity savedEntity = new RestauranteEntity();
        Restaurante expectedRestaurante = new Restaurante();

        Mockito.when(restauranteEntityMapper.toRestauranteEntity(inputRestaurante)).thenReturn(entityToSave);
        Mockito.when(repository.save(entityToSave)).thenReturn(savedEntity);
        Mockito.when(restauranteEntityMapper.toRestauranteModel(savedEntity)).thenReturn(expectedRestaurante);


        Restaurante result = adapter.saveRestaurante(inputRestaurante);


        assertEquals(expectedRestaurante, result);
        Mockito.verify(restauranteEntityMapper).toRestauranteEntity(inputRestaurante);
        Mockito.verify(repository).save(entityToSave);
        Mockito.verify(restauranteEntityMapper).toRestauranteModel(savedEntity);
    }

    @Test
    @Order(2)
    void getRestauranteaBy_debeRetornarRestauranteCuandoExiste() {

        Long id = 1L;
        Long idPropietario = 1L;
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        Restaurante restaurante = new Restaurante();

        when(repository.findByIdAndIdPropietario(id, idPropietario)).thenReturn(Optional.of(restauranteEntity));
        when(restauranteEntityMapper.toRestauranteModel(restauranteEntity)).thenReturn(restaurante);

        Restaurante resultado = adapter.findByIdAndIdPropietario(id, idPropietario);

        assertNotNull(resultado);
        verify(repository).findByIdAndIdPropietario(id, idPropietario);
        verify(restauranteEntityMapper).toRestauranteModel(restauranteEntity);


    }

    @Test
    @Order(3)
    void getUsuarioByCorreo_debeLanzarExcepcionCuandoNoExiste() {
        Long id = 1000L;
        Long idPropietario = 1000L;
        when(repository.findByIdAndIdPropietario(id, idPropietario)).thenReturn(Optional.empty());

        Restaurante resultado = adapter.findByIdAndIdPropietario(id, idPropietario);

        assertNull(resultado);
        verify(repository).findByIdAndIdPropietario(id, idPropietario);
        verifyNoInteractions(restauranteEntityMapper);
    }

    @Test
    @Order(4)
    void findAllRestaurantes_deberiaRetornarPageResponseCorrecto() {

        int page = 0;
        int size = 2;

        RestauranteEntity entity = new RestauranteEntity();
        Restaurante model = new Restaurante();


        Page<RestauranteEntity> pageMock = new PageImpl<>(
                Collections.singletonList(entity),
                PageRequest.of(page, size, Sort.by("nombre")),
                5
        );

        when(repository.findAll(any(Pageable.class))).thenReturn(pageMock);
        when(restauranteEntityMapper.toRestauranteModel(entity)).thenReturn(model);


        PageResponse<Restaurante> response = adapter.findAllRestaurantes(page, size);


        assertEquals(1, response.getContent().size());
        assertEquals(model, response.getContent().get(0));
        assertEquals(0, response.getCurrentPage());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertTrue(response.isHasNext());
        assertFalse(response.isHasPrevious());

        verify(repository).findAll(any(Pageable.class));
        verify(restauranteEntityMapper).toRestauranteModel(entity);
    }

}
