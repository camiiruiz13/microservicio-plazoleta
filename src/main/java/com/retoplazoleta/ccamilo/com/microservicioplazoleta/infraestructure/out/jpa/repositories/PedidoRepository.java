package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<EstadoPedido> estados);

    @Query(value = """
    SELECT DISTINCT p
    FROM PedidoEntity p
    JOIN p.platos pp
    JOIN pp.plato pl
    WHERE p.estado = :estado
    AND p.restaurante.id = :idRestaurante
    AND pl.activo = true
    """,
            countQuery = """
    SELECT COUNT(DISTINCT p)
    FROM PedidoEntity p
    JOIN p.platos pp
    JOIN pp.plato pl
    WHERE p.estado = :estado
    AND p.restaurante.id = :idRestaurante
    AND pl.activo = true
    """)
    Page<PedidoEntity> findPedidosConPlatosActivos(
            @Param("estado") EstadoPedido estado,
            @Param("idRestaurante") Long idRestaurante,
            Pageable pageable);


List<PedidoEntity> findByRestauranteEntityId(Long idRestaurante);

}