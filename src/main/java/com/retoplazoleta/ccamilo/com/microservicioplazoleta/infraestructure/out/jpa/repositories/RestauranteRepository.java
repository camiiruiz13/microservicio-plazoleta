package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

    Optional<RestauranteEntity> findByIdAndIdPropietario(Long id, Long idPropietario);
}