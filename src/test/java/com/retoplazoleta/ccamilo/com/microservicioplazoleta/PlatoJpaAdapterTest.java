package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
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
class PlatoJpaAdapterTest {

    @Mock
    private IPlatoEntityMapper platoEntityMapper;

    @Mock
    private PlatoRepository repository;

    @InjectMocks
    private PlatoJpaAdapter adapter;

    @Test
    @Order(1)
    void savePlato_ShouldMapAndPersistAndReturnPlato() {

        Plato inputPlato = new Plato();
        PlatoEntity entityToSave = new PlatoEntity();
        PlatoEntity savedEntity = new PlatoEntity();
        Plato expectedPlato = new Plato();

        Mockito.when(platoEntityMapper.toPlatontity(inputPlato)).thenReturn(entityToSave);
        Mockito.when(repository.save(entityToSave)).thenReturn(savedEntity);
        Mockito.when(platoEntityMapper.toPlatoModel(savedEntity)).thenReturn(expectedPlato);


        Plato result = adapter.savePlato(inputPlato);


        Assertions.assertEquals(expectedPlato, result);
        Mockito.verify(platoEntityMapper).toPlatontity(inputPlato);
        Mockito.verify(repository).save(entityToSave);
        Mockito.verify(platoEntityMapper).toPlatoModel(savedEntity);
    }

    @Test
    @Order(2)
    void getPlatoById_debeRetornarCategoriaPlatoExisteAdapter() {

        Long id = 1L;
        PlatoEntity platoEntity = new PlatoEntity();
        Plato plato = new Plato();

        when(repository.findById(id)).thenReturn(Optional.of(platoEntity));
        when(platoEntityMapper.toPlatoModel(platoEntity)).thenReturn(plato);

        Plato resultado = adapter.findById(id);

        assertNotNull(resultado);
        verify(repository).findById(id);
        verify(platoEntityMapper).toPlatoModel(platoEntity);


    }

    @Test
    @Order(3)
    void getPlatooById_debeLanzarExcepcionCuandoNoExisteAdapter() {
        Long id = 1000L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Plato resultado = adapter.findById(id);

        assertNull(resultado);
        verify(repository).findById(id);
        verifyNoInteractions(platoEntityMapper);
    }
}
