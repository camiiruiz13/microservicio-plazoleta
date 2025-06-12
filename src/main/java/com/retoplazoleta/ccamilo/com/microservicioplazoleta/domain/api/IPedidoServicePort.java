package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;

public interface IPedidoServicePort {

    void savePedido(Pedido pedido);
}
