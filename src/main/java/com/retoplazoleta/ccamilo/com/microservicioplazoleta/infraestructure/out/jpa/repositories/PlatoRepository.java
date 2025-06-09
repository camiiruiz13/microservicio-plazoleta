
package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {

    @Query("""
                SELECT p FROM PlatoEntity p
                WHERE p.id = :idPlato
                AND p.restaurante.id IN (
                    SELECT r.id
                    FROM RestauranteEntity r
                    WHERE r.id = p.restaurante.id
                    AND r.idPropietario = :idPropietario
                )
            """)
    Optional<PlatoEntity> findByIdAndIdPropietario(Long idPlato, Long idPropietario);

}