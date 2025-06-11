package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.CategoriaDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CategoriaResponseMapper {
    @Mapping(source = "idCategoria", target = "id")
    Categoria toModelCategoria(CategoriaDTO categoriaDTO);

    @Mapping(source = "id", target = "idCategoria")
    CategoriaDTO toDto(Categoria categoria);


}