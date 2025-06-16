package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoPlatoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPedidoPlatoResponseMapper {

    PedidoPlatoDTOResponse toPedidoPlatoDTO(PedidoPlato pedidoPlato);

    List<PedidoPlatoDTOResponse> toPedidoPlatoDTOList(List<PedidoPlato> pedidoPlatos);
}
