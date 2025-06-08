package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaEntityMapper categoriaEntityMapper;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria findByIdCategoria(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .map(categoriaEntityMapper::toCategoriaModel)
                .orElse(null);
    }
}
