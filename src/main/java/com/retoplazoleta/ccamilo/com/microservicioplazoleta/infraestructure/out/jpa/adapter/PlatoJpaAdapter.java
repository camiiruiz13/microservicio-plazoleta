package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PlatoJpaAdapter  implements IPlatoPersistencePort {

    private final IPlatoEntityMapper platoEntityMapper;
    private final PlatoRepository platoRepository;

    @Override
    public Plato savePlato(Plato plato) {
        PlatoEntity platoEntity = platoRepository.save(platoEntityMapper.toPlatontity(plato));
        return platoEntityMapper.toPlatoModel(platoEntity
        );
    }

    @Override
    public Plato findByIdAndIdPropietario(Long id, Long idPropietario) {
        return platoRepository.findByIdAndIdPropietario(id, idPropietario)
                .map(platoEntityMapper::toPlatoModel)
                .orElse(null);
    }

    @Override
    public PageResponse<Plato> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<PlatoEntity> platosPaginados = platoRepository
                .findPlatosPorRestauranteYCategoria(idRestaurante, idCategoria, pageable);

        List<Plato> platos = platoEntityMapper.toPlatoModelList(platosPaginados.getContent());
        
        return PageResponse.<Plato>builder()
                .content(platos)
                .currentPage(platosPaginados.getNumber())
                .pageSize(platosPaginados.getSize())
                .totalElements(platosPaginados.getTotalElements())
                .totalPages(platosPaginados.getTotalPages())
                .hasNext(platosPaginados.hasNext())
                .hasPrevious(platosPaginados.hasPrevious())
                .build();
    }
    


}
