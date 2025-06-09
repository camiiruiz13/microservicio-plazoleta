package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IPlatoEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.PlatoRepository;
import lombok.RequiredArgsConstructor;

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


}
