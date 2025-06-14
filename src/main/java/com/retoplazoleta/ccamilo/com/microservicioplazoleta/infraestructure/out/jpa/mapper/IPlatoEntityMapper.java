package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPlatoEntityMapper {
    PlatoEntity toPlatontity(Plato plato);
    Plato toPlatoModel(PlatoEntity platoEntity);
    List<Plato> toPlatoModelList(List<PlatoEntity> platoEntities);
    List<PlatoEntity> toPlatoEntities(List<Plato> platos);
}
