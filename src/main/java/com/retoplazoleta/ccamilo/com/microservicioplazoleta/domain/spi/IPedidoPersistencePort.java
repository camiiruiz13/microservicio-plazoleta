package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;

import java.util.List;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);

    boolean clientFindPedidoProcess(Long idCliente);

    PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante,  int page, int pageSize);

     Pedido findById(Long id);

    List<PedidoTrace> findByIdRestaurant(Long idRestaurante);
}
