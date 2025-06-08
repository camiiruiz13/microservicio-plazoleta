package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}