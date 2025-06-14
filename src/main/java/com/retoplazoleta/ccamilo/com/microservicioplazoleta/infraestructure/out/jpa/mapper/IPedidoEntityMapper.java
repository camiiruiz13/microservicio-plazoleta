package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IRestauranteEntityMapper.class, PedidoPlatoEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoEntityMapper {
    Pedido toModel(PedidoEntity entity);
    PedidoEntity toEntity(Pedido model);
    List<Pedido> toModelList(List<PedidoEntity> entityList);
}
