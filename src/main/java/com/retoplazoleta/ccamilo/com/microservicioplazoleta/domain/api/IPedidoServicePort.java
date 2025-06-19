package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IPedidoServicePort {

    void savePedido(Pedido pedido);
    void updatePedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) ;

    PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante,Long idChef, int page, int pageSize);
    Pedido findById(Long id);
}
