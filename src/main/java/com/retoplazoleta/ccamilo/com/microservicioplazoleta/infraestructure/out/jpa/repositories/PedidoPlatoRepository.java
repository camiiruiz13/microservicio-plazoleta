package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoIdEmbeded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoPlatoRepository extends JpaRepository<PedidoPlatoEntity, PedidoPlatoIdEmbeded> {

}