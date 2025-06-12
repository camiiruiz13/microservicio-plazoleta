package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoRequestMapper pedidoRequestMapper;


    @Override
    public void savePedido(PedidoDTO pedidoDTO, Long idCliente) {
        Pedido pedido = pedidoRequestMapper.toPedido(pedidoDTO);
        pedido.setIdCliente(idCliente);
        pedidoServicePort.savePedido(pedido);
    }
}
