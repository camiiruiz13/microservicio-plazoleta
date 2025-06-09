package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util.SwaggerConstants.*;


/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoDTOUpdate {

    @Schema(description = PLATO_DESC_DESC, example = PLATO_DESC_EXAMPLE, type = STRING_TYPE)
    private String descripcion;

    @Schema(description = PLATO_PRECIO_DESC, example = PLATO_PRECIO_EXAMPLE, type = NUMBER_TYPE, format = FORMAT_DOUBLE)
    private Double precio;


}