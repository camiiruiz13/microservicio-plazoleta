package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoPlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPedidoRequestMapper {

    @Mapping(target = "restaurante.id", source = "idRestaurante")
    @Mapping(target = "idCliente", source = "idCliente")
    @Mapping(target = "platos", source = "platos")
    Pedido toPedido(PedidoDTO dto);

    @Mapping(target = "restaurante.id", source = "idRestaurante")
    Pedido toPedidoUpdate(PedidoUpdateDTO pedidoDTO);

    @Mapping(target = "restaurante.id", source = "idRestaurante")
    @Mapping(target = "pinSeguridad", source = "codigo")
    Pedido toPedidoDeliver(PedidoDeliverDTO pedidoDTO);

    List<PedidoPlato> toPedidoPlatoList(List<PedidoPlatoDTO> dtos);
}
