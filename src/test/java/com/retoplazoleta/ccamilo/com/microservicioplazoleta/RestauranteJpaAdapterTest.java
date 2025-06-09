package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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


        Assertions.assertEquals(expectedRestaurante, result);
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
}
