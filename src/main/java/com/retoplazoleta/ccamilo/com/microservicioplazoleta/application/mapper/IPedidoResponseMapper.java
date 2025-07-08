package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoTraceDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {IRestauranteResponseMapper.class, IPedidoPlatoResponseMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPedidoResponseMapper {

    @Mapping(target = "idPedido", source = "id")
    @Mapping(target = "estado", expression = "java(pedido.getEstado().name())")
    PedidoDTOResponse toResponse(Pedido pedido);

    @Mapping(target = "idPedido", source = "id")
    @Mapping(target = "estado", expression = "java(pedidoTrace.getEstado().name())")
    PedidoTraceDTOResponse toPedidoTraceResponse(PedidoTrace pedidoTrace);

    List<PedidoDTOResponse> toResponseList(List<Pedido> pedidos);

    List<PedidoTraceDTOResponse> toPedidoTraceResponseList(List<PedidoTrace> pedidoTraces);
}
