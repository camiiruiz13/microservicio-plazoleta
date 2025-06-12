package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;

import java.util.List;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);

    List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos);

    boolean clientFindPedidoProcess(Long idCliente);

    boolean existsPlatosOfRestaurant(List<Long> idsPlatos, Long idRestaurante);

}
