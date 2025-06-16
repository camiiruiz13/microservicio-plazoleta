package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.PedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido.PENDIENTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private IPedidoRequestMapper pedidoRequestMapper;

    @Mock
    private IPedidoResponseMapper pedidoResponseMapper;

    @InjectMocks
    private PedidoHandler pedidoHandler;


    @Test
    @Order(1)
    void savePPedido_deberiaGuardarCorrectamente() {

        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = new Pedido();
        when(pedidoRequestMapper.toPedido(pedidoDTO)).thenReturn(pedido);
        pedidoHandler.savePedido(pedidoDTO);
        verify(pedidoRequestMapper).toPedido(pedidoDTO);
        verify(pedidoServicePort).savePedido(pedido);

    }

    @Test
    @Order(2)
    void testFindPedidosRestaurante() {
        Long idRestaurante = 1L;
        Long idChef = 1L;
        int page = 0;
        int pageSize = 5;


        Pedido pedido = new Pedido();
        PedidoDTOResponse pedidoDTOResponse = new PedidoDTOResponse();


        PageResponse<Pedido> pageResponse = PageResponse.<Pedido>builder()
                .content(List.of(pedido))
                .totalPages(1)
                .totalElements(1L)
                .currentPage(page)
                .pageSize(pageSize)
                .hasNext(false)
                .hasPrevious(false)
                .build();


        when(pedidoServicePort.findByEstadoAndRestauranteId(PENDIENTE, idRestaurante, idChef, page, pageSize)).thenReturn(pageResponse);
        when(pedidoResponseMapper.toResponse(pedido)).thenReturn(pedidoDTOResponse);


        PageResponseDTO<PedidoDTOResponse> result = pedidoHandler.findByEstadoAndRestauranteId(PENDIENTE.name(), idRestaurante, idChef, page, pageSize);


        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertEquals(page, result.getCurrentPage());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(1, result.getContent().size());
        assertEquals(pedidoDTOResponse, result.getContent().get(0));


        verify(pedidoServicePort).findByEstadoAndRestauranteId(PENDIENTE, idRestaurante, idChef, page, pageSize);
        verify(pedidoResponseMapper).toResponse(pedido);
    }

}
