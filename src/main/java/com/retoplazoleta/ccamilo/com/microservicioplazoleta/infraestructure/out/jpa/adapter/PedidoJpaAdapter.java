package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPedidoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.PedidoPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoPlatoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;

    @Override
    public Pedido savePedido(Pedido pedido) {
        return null;
    }

    @Override
    public List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos) {
        return List.of();
    }

    @Override
    public boolean clientFindPedidoProcess(Long idCliente) {
        return false;
    }

    @Override
    public boolean existsPlatosOfRestaurant(List<Long> idsPlatos, Long idRestaurante) {
        return false;
    }
}
