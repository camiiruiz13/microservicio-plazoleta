package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;

public interface IPedidoHandler {

    void savePlato(PedidoDTO pedido, Long idCliente);
}
