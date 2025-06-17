package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

import java.util.List;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);

    List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos, Pedido pedido, List<Plato> platos);

    boolean clientFindPedidoProcess(Long idCliente);

    PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante,  int page, int pageSize);
    PageResponse<Pedido> findByEstadoAndRestauranteIdChef(EstadoPedido estado, Long idRestaurante, Long idChef, int page, int pageSize);
    Pedido findById(Long id);





}
