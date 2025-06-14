package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDTOResponse {

    private Long idRestaurante;
    private String nombre;
    private String direccion;
    private Long idPropietario;
    private String telefono;
    private String urlLogo;
    private String nit;
}
