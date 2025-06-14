package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IPedidoServicePort {

    void savePedido(Pedido pedido);

    PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante, int page, int pageSize);
}
