package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {

    private Long id;
    private String nombre;
    private String direccion;
    private Long idPropietario;
    private String telefono;
    private String urlLogo;
    private String nit;
}
