package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long idCategoria;
    private String nombre;
    private String descripcion;
}