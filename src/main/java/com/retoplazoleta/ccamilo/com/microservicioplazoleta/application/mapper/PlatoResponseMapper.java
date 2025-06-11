package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PlatoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = CategoriaResponseMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface PlatoResponseMapper {

    @Mapping(source = "id", target = "idPlato")
    PlatoDTOResponse toPlatoDTo(Plato plato);

    @Mapping(source = "idPlato", target = "id")
    Plato toPlatoModel(PlatoDTOResponse platoDTO);
}