package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.CategoriaEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private Long id;
    private String nombre;
    private String descripcion;
}
