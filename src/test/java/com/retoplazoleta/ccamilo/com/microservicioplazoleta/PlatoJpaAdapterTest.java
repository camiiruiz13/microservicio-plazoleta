package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void getPlatoByIdAndIdPropietario_debeRetornarCategoriaPlatoExisteAdapter() {

        Long id = 1L;
        Long idPropietario = 1L;
        PlatoEntity platoEntity = new PlatoEntity();
        Plato plato = new Plato();

        when(repository.findByIdAndIdPropietario(id, idPropietario)).thenReturn(Optional.of(platoEntity));
        when(platoEntityMapper.toPlatoModel(platoEntity)).thenReturn(plato);

        Plato resultado = adapter.findByIdAndIdPropietario(id, idPropietario);

        assertNotNull(resultado);
        verify(repository).findByIdAndIdPropietario(id, idPropietario);
        verify(platoEntityMapper).toPlatoModel(platoEntity);


    }

    @Test
    @Order(3)
    void getPlatooByAndIdPropietario__debeLanzarExcepcionCuandoNoExisteAdapter() {
        Long id = 1000L;
        Long idPropietario = 1000L;
        when(repository.findByIdAndIdPropietario(id, idPropietario)).thenReturn(Optional.empty());

        Plato resultado = adapter.findByIdAndIdPropietario(id, idPropietario);

        assertNull(resultado);
        verify(repository).findByIdAndIdPropietario(id, idPropietario);
        verifyNoInteractions(platoEntityMapper);
    }

    @Test
    @Order(4)
    void findAllPlatoPage_deberiaRetornarPageResponseCorrecto() {
        int page = 0;
        int size = 2;
        Long idRestaurante = 1L;
        Long idCategoria = 1L;

        PlatoEntity entity = new PlatoEntity();
        Plato model = new Plato();

        Page<PlatoEntity> pageMock = new PageImpl<>(
                Collections.singletonList(entity),
                PageRequest.of(page, size),
                5
        );

        when(repository.findPlatosPorRestauranteYCategoria(eq(idRestaurante), eq(idCategoria), any(Pageable.class)))
                .thenReturn(pageMock);
        when(platoEntityMapper.toPlatoModelList(anyList()))
                .thenReturn(Collections.singletonList(model));

        PageResponse<Plato> response = adapter.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, size);

        assertEquals(1, response.getContent().size());
        assertEquals(model, response.getContent().get(0));
        assertEquals(0, response.getCurrentPage());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertTrue(response.isHasNext());
        assertFalse(response.isHasPrevious());

        verify(repository).findPlatosPorRestauranteYCategoria(eq(idRestaurante), eq(idCategoria), any(Pageable.class));
        verify(platoEntityMapper).toPlatoModelList(anyList());
    }

}
