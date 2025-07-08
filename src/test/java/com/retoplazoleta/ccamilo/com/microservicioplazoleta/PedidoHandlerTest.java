package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoTraceDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.PedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
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

    private final String token = "dummyToken";


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


        when(pedidoServicePort.findByEstadoAndRestauranteId(PENDIENTE, idRestaurante, page, pageSize)).thenReturn(pageResponse);
        when(pedidoResponseMapper.toResponse(pedido)).thenReturn(pedidoDTOResponse);

        PageResponseDTO<PedidoDTOResponse> result = pedidoHandler.findByEstadoAndRestauranteId(PENDIENTE.name(), idRestaurante, page, pageSize);

        assertNotNull(result);
    }

    @Test
    @Order(3)
    void asignarPedido_deberiaGuardarCorrectamente() {
        Long idPedido = 1L;
        PedidoUpdateDTO pedidoDTO = new PedidoUpdateDTO();
        Pedido pedido = new Pedido();
        when(pedidoRequestMapper.toPedidoUpdate(pedidoDTO)).thenReturn(pedido);
        pedidoHandler.asignarPedido(idPedido, pedidoDTO, token);
        verify(pedidoRequestMapper).toPedidoUpdate(pedidoDTO);
    }

    @Test
    @Order(4)
    void notificarPedido_deberiaGuardarCorrectamente() {
        Long idPedido = 1L;
        PedidoUpdateDTO pedidoDTO = new PedidoUpdateDTO();
        Pedido pedido = new Pedido();
        when(pedidoRequestMapper.toPedidoUpdate(pedidoDTO)).thenReturn(pedido);
        pedidoHandler.notificarPedido(idPedido, pedidoDTO, token);
        verify(pedidoRequestMapper).toPedidoUpdate(pedidoDTO);
    }

    @Test
    @Order(5)
    void entregarPedido_deberiaGuardarCorrectamente() {
        Long idPedido = 1L;
        PedidoDeliverDTO pedidoDTO = new PedidoDeliverDTO();
        Pedido pedido = new Pedido();
        when(pedidoRequestMapper.toPedidoDeliver(pedidoDTO)).thenReturn(pedido);
        pedidoHandler.entregarPedido(idPedido, pedidoDTO, token);
        verify(pedidoRequestMapper).toPedidoDeliver(pedidoDTO);
    }

    @Test
    @Order(6)
    void cancelarPedido_deberiaGuardarCorrectamente() {
        Long idPedido = 1L;
        PedidoUpdateDTO pedidoDTO = new PedidoUpdateDTO();
        Pedido pedido = new Pedido();
        when(pedidoRequestMapper.toPedidoUpdate(pedidoDTO)).thenReturn(pedido);
        pedidoHandler.cancelarPedido(idPedido, pedidoDTO, token);
        verify(pedidoRequestMapper).toPedidoUpdate(pedidoDTO);
    }

    @Test
    @Order(7)
    void findPedidoByIdRestaurant() {
        Long idRestaurante = 1L;
        Long idPropietario = 1L;
        String token = "dummyToken";

        PedidoTrace pedidoTrace = new PedidoTrace();
        PedidoTraceDTOResponse pedidoDTOResponse = new PedidoTraceDTOResponse();

        List<PedidoTrace> pedidoTraceList = List.of(pedidoTrace);
        List<PedidoTraceDTOResponse> dtoList = List.of(pedidoDTOResponse);

        when(pedidoServicePort.findByIdRestaurant(idRestaurante, idPropietario, token))
                .thenReturn(pedidoTraceList);


        when(pedidoResponseMapper.toPedidoTraceResponseList(anyList()))
                .thenReturn(dtoList);

        List<PedidoTraceDTOResponse> result = pedidoHandler.findPedidoByIdRestaurant(idRestaurante, idPropietario, token);

        assertNotNull(result);

    }



}
