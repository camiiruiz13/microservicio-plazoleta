package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface PlatoRequestMapper {

    @Mapping(target = "categoria.id", source = "idCategoria")
    @Mapping(target = "restaurante.id", source = "idRestaurante")
    @Mapping(target = "restaurante.idPropietario", source = "idPropietario")
    Plato toPlato(PlatoDTO dto);
    Plato toPlatoUpdate(PlatoDTOUpdate platoDTO);
}
