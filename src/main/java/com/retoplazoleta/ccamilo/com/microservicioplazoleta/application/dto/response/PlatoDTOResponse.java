package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoDTOResponse {
    private Long idPlato;
    private Long idCategoria;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String urlImagen;
    private CategoriaDTO categoria;
    private Boolean activo;
}