package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPedidoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.PedidoPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoPlatoRepository;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public Pedido savePedido(Pedido pedido) {
        PedidoEntity entity = pedidoEntityMapper.toEntity(pedido);
        return pedidoEntityMapper.toModel(pedidoRepository.save(entity));

    }

    @Override
    public List<PedidoPlato> savePedidoPlatos(List<PedidoPlato> pedidoPlatos, Pedido pedido, List<Plato> platos) {
        List<PedidoPlatoEntity> pedidoPlatoEntities = pedidoPlatoEntityMapper.toEntityList(pedidoPlatos);
        PedidoEntity pedidoEntity  = pedidoEntityMapper.toEntity(pedido);
        List<PlatoEntity> platoEntities = platoEntityMapper.toPlatoEntities(platos);

        Map<Long, PlatoEntity> platoEntityMap = platoEntities.stream()
                .collect(Collectors.toMap(PlatoEntity::getId, Function.identity()));

        for (PedidoPlatoEntity entity : pedidoPlatoEntities) {
            entity.setPedido(pedidoEntity);
            entity.setPlato(platoEntityMap.get(entity.getId().getIdPlato()));
        }



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
    public PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<PedidoEntity> pedidoPaginados = pedidoRepository.findByEstadoAndRestauranteIdEntities(estado, idRestaurante, pageable);
        List<Pedido> pedidos = pedidoEntityMapper.toModelList(pedidoPaginados.getContent());
        return PageResponse.<Pedido>builder()
                .content(pedidos)
                .currentPage(pedidoPaginados.getNumber())
                .pageSize(pedidoPaginados.getSize())
                .totalElements(pedidoPaginados.getTotalElements())
                .totalPages(pedidoPaginados.getTotalPages())
                .hasNext(pedidoPaginados.hasNext())
                .hasPrevious(pedidoPaginados.hasPrevious())
                .build();

    }


}
