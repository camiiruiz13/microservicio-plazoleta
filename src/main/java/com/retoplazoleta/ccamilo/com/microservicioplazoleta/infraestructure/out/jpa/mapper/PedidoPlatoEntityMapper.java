package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoIdEmbeded;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface PedidoPlatoEntityMapper {

    @Mapping(target = "id", expression = "java(mapId(model.getIdPedido(), model.getIdPlato()))")
    @Mapping(target = "pedido", expression = "java(injectPedido(pedidoEntity))")
    @Mapping(target = "plato", expression = "java(mapPlatoFromId(model.getIdPlato()))")
    PedidoPlatoEntity toEntity(PedidoPlato model, @Context PedidoEntity pedidoEntity);

    List<PedidoPlatoEntity> toEntityList(List<PedidoPlato> models, @Context PedidoEntity pedidoEntity);

    @Mapping(target = "idPedido", source = "id.idPedido")
    @Mapping(target = "idPlato", source = "id.idPlato")
    PedidoPlato toModel(PedidoPlatoEntity entity);

    List<PedidoPlato> toModelList(List<PedidoPlatoEntity> entities);

    default PedidoEntity injectPedido(@Context PedidoEntity pedidoEntity) {
        return pedidoEntity;
    }


    default PedidoPlatoIdEmbeded mapId(Long idPedido, Long idPlato) {
        PedidoPlatoIdEmbeded id = new PedidoPlatoIdEmbeded();
        id.setIdPedido(idPedido);
        id.setIdPlato(idPlato);
        return id;
    }

    default PedidoEntity mapPedidoFromId(Long idPedido) {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setId(idPedido);
        return pedido;
    }

    default PlatoEntity mapPlatoFromId(Long idPlato) {
        PlatoEntity plato = new PlatoEntity();
        plato.setId(idPlato);
        return plato;
    }
}
