package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoriaEntityMapper {
    CategoriaEntity toCategoriantity(Categoria categoria);
    Categoria toCategoriaModel(CategoriaEntity categoriaEntity);
}
