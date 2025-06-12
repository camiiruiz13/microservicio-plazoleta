package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;

import java.util.List;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);

    List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos, Pedido pedido, List<Plato> platos);

    boolean clientFindPedidoProcess(Long idCliente);



}
