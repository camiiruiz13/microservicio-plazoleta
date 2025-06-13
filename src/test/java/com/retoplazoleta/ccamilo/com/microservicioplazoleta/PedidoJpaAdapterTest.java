package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.PedidoJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoIdEmbeded;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
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

import java.util.List;
import java.util.Map;

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
}