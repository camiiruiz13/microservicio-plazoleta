package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PedidoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.*;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPedidoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.PedidoPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoPlatoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoJpaAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PedidoPlatoRepository pedidoPlatoRepository;
    @Mock
    private IPedidoEntityMapper pedidoEntityMapper;
    @Mock
    private PedidoPlatoEntityMapper pedidoPlatoEntityMapper;
    @Mock
    private IPlatoEntityMapper platoEntityMapper;

    @InjectMocks
    private PedidoJpaAdapter adapter;

    @Test
    @Order(1)
    void savePedido_deberiaRetornarPedidoGuardado() {
        Pedido pedido = new Pedido();
        PedidoEntity pedidoEntity = new PedidoEntity();
        PedidoEntity savedEntity = new PedidoEntity();
        Pedido expectedModel = new Pedido();

        when(pedidoEntityMapper.toEntity(pedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(pedidoEntity)).thenReturn(savedEntity);
        when(pedidoEntityMapper.toModel(savedEntity)).thenReturn(expectedModel);

        Pedido result = adapter.savePedido(pedido);

        assertEquals(expectedModel, result);
        verify(pedidoEntityMapper).toEntity(pedido);
        verify(pedidoRepository).save(pedidoEntity);
        verify(pedidoEntityMapper).toModel(savedEntity);
    }

    @Test
    @Order(2)
    void savePedidoPlatos_deberiaGuardarYRetornarLista() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1L);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setIdPedido(1L);
        pedidoPlato.setIdPlato(10L);
        pedidoPlato.setCantidad(3);

        List<PedidoPlato> pedidoPlatoList = List.of(pedidoPlato);

        PedidoPlatoEntity entity = new PedidoPlatoEntity();
        PedidoPlatoIdEmbeded pedidoPlatoIdEmbeded = new PedidoPlatoIdEmbeded();
        pedidoPlatoIdEmbeded.setIdPedido(1L);
        pedidoPlatoIdEmbeded.setIdPlato(10L);
        entity.setId(pedidoPlatoIdEmbeded);

        Plato plato = new Plato(10L, null, null, null, null, null, null, null);
        PlatoEntity platoEntity = new PlatoEntity();
        platoEntity.setId(10L);

        when(pedidoPlatoEntityMapper.toEntityList(pedidoPlatoList)).thenReturn(List.of(entity));
        when(pedidoEntityMapper.toEntity(pedido)).thenReturn(pedidoEntity);
        when(platoEntityMapper.toPlatoEntities(List.of(plato))).thenReturn(List.of(platoEntity));
        when(pedidoPlatoRepository.saveAll(any())).thenReturn(List.of(entity));
        when(pedidoPlatoEntityMapper.toModelList(any())).thenReturn(List.of(pedidoPlato));

        List<PedidoPlato> result = adapter.savePedidoPlatos(pedidoPlatoList, pedido, List.of(plato));

        assertEquals(1, result.size());
        verify(pedidoPlatoEntityMapper).toEntityList(pedidoPlatoList);
        verify(pedidoEntityMapper).toEntity(pedido);
        verify(platoEntityMapper).toPlatoEntities(List.of(plato));
        verify(pedidoPlatoRepository).saveAll(any());
        verify(pedidoPlatoEntityMapper).toModelList(any());
    }

    @Test
    @Order(3)
    void clientFindPedidoProcess_deberiaRetornarTrueSiExiste() {
        Long idCliente = 1L;
        when(pedidoRepository.existsByIdClienteAndEstadoIn(eq(idCliente), anyList()))
                .thenReturn(true);

        assertTrue(adapter.clientFindPedidoProcess(idCliente));
        verify(pedidoRepository).existsByIdClienteAndEstadoIn(eq(idCliente), anyList());
    }

    @Test
    @Order(4)
    void clientFindPedidoProcess_deberiaRetornarFalseSiNoExiste() {
        Long idCliente = 2L;
        when(pedidoRepository.existsByIdClienteAndEstadoIn(eq(idCliente), anyList()))
                .thenReturn(false);

        assertFalse(adapter.clientFindPedidoProcess(idCliente));
        verify(pedidoRepository).existsByIdClienteAndEstadoIn(eq(idCliente), anyList());
    }

    @Test
    @Order(5)
    void findPedidoByPendiente_deberiaRetornarPageResponseCorrecto() {

        Long idRestaurante = 1L;
        int page = 0;
        int size = 2;

        PedidoEntity entity = new PedidoEntity();
        Pedido model = new Pedido();


        Page<PedidoEntity> pageMock = new PageImpl<>(
                Collections.singletonList(entity),
                PageRequest.of(page, size),
                5
        );

        when(pedidoRepository.findByEstadoAndRestaurante_Id(
                any(EstadoPedido.class),
                eq(idRestaurante),
                any(Pageable.class)
        )).thenReturn(pageMock);

        when(pedidoEntityMapper.toModelList(Collections.singletonList(entity)))
                .thenReturn(Collections.singletonList(model));


        PageResponse<Pedido> response = adapter.findByEstadoAndRestauranteId(EstadoPedido.PENDIENTE,idRestaurante, page, size);


        assertEquals(1, response.getContent().size());
        assertEquals(model, response.getContent().get(0));
        assertEquals(0, response.getCurrentPage());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertTrue(response.isHasNext());
        assertFalse(response.isHasPrevious());

        verify(pedidoRepository).findByEstadoAndRestaurante_Id(
                any(EstadoPedido.class),
                eq(idRestaurante),
                any(Pageable.class)
        );

        verify(pedidoEntityMapper).toModelList(Collections.singletonList(entity));


    }

    @Test
    @Order(6)
    void findPedidoByPendienteAndIdChef_deberiaRetornarPageResponseCorrecto() {

        Long idRestaurante = 1L;
        Long idChef = 1L;
        int page = 0;
        int size = 2;

        PedidoEntity entity = new PedidoEntity();
        Pedido model = new Pedido();


        Page<PedidoEntity> pageMock = new PageImpl<>(
                Collections.singletonList(entity),
                PageRequest.of(page, size),
                5
        );

        when(pedidoRepository.findByEstadoAndRestauranteAndChefConditionally(
                any(EstadoPedido.class),
                eq(idRestaurante),
                eq(idChef),
                any(Pageable.class)
        )).thenReturn(pageMock);

        when(pedidoEntityMapper.toModelList(Collections.singletonList(entity)))
                .thenReturn(Collections.singletonList(model));


        PageResponse<Pedido> response = adapter.findByEstadoAndRestauranteIdChef(EstadoPedido.EN_PREPARACION,idRestaurante, idChef, page, size);


        assertEquals(1, response.getContent().size());
        assertEquals(model, response.getContent().get(0));
        assertEquals(0, response.getCurrentPage());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertTrue(response.isHasNext());
        assertFalse(response.isHasPrevious());

        verify(pedidoRepository).findByEstadoAndRestauranteAndChefConditionally(
                any(EstadoPedido.class),
                eq(idRestaurante),
                eq(idChef),
                any(Pageable.class)
        );

        verify(pedidoEntityMapper).toModelList(Collections.singletonList(entity));


    }

    @Test
    @Order(7)
    void getPedidoById() {

        Long id = 1L;
        PedidoEntity pedidoEntity = new PedidoEntity();
        Pedido pedido = new Pedido();

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoEntityMapper.toModel(pedidoEntity)).thenReturn(pedido);

        Pedido resultado = adapter.findById(id);

        assertNotNull(resultado);
        verify(pedidoRepository).findById(id);
        verify(pedidoEntityMapper).toModel(pedidoEntity);

    }
}