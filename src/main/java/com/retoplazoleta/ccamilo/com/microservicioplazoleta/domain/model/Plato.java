package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PlatoEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plato {

    private Long id;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private Double precio;
    private Restaurante restaurante;
    private String urlImagen;
    private Boolean activo;
}
