package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {IPlatoEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoPlatoEntityMapper {
    @Mapping(target = "idPedido", source = "id.idPedido")
    @Mapping(target = "idPlato", source = "id.idPlato")
    PedidoPlato toModel(PedidoPlatoEntity entity);

    @Mapping(target = "id.idPedido", source = "idPedido")
    @Mapping(target = "id.idPlato", source = "idPlato")
    PedidoPlatoEntity toEntity(PedidoPlato model);
}
