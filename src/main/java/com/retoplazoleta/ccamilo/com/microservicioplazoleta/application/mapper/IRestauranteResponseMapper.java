package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestauranteResponseMapper {

    RestauranteDTOPage toResponseRestauranteList(Restaurante restauranteList);
    @Mapping(target = "idRestaurante", source = "id")
    RestauranteDTOResponse toRestauranteModel(Restaurante restaurante);
}
