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
        PedidoEntity pedidoEntity = pedidoEntityMapper.toEntity(pedido);
        PedidoEntity pedidoGuardado = pedidoRepository.save(pedidoEntity);
        List<PedidoPlatoEntity> pedidoPlatos = pedidoPlatoEntityMapper.toEntityList(pedido.getPlatos(), pedidoGuardado);
        pedidoPlatoRepository.saveAll(pedidoPlatos);
        return pedidoEntityMapper.toModel(pedidoGuardado);
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
        Page<PedidoEntity> pedidoPaginados = pedidoRepository.findPedidosConPlatosActivos(estado, idRestaurante, pageable);
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


    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoEntityMapper::toModel)
                .orElse(null);
    }


}
