package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.PedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private IPedidoRequestMapper pedidoRequestMapper;

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

}
