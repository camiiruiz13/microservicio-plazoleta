package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPedidoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.PedidoPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoPlatoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;
    private final PlatoRepository platoRepository;

    @Override
    public Pedido savePedido(Pedido pedido) {
        PedidoEntity entity = pedidoEntityMapper.toEntity(pedido);
        return pedidoEntityMapper.toModel(pedidoRepository.save(entity));

    }

    @Override
    public List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos) {
        List<PedidoPlatoEntity> pedidoPlatoEntities = pedidoPlatoEntityMapper.toEntityList(pedidoPlatos);
        return pedidoPlatoEntityMapper.toModelList(pedidoPlatoRepository.saveAll(pedidoPlatoEntities));
    }

    @Override
    public boolean clientFindPedidoProcess(Long idCliente) {
        return pedidoRepository.existsByIdClienteAndEstadoIn(
                idCliente,
                List.of(EstadoPedido.PENDIENTE, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO)
        );
    }

    @Override
    public boolean existsPlatosOfRestaurant(List<Long> idsPlatos, Long idRestaurante) {
        return platoRepository.existsByIdInAndRestaurante_IdNot(idsPlatos, idRestaurante);
    }


}
