package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

    boolean existsByIdPropietarioAndId(Long idPropietario, Long id);
}