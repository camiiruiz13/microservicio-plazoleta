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
public class PlatoDTO {

    @Schema(description = PLATO_NOMBRE_DESC, example = PLATO_NOMBRE_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = PLATO_DESC_DESC, example = PLATO_DESC_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Schema(description = PLATO_PRECIO_DESC, example = PLATO_PRECIO_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_DOUBLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precio;

    @Schema(description = IMAGE_DESC, example = IMAGE_EXAMPLE , type =STRING_TYPE,  requiredMode = Schema.RequiredMode.REQUIRED)
    private String urlImagen;


    @Schema(description = PLATO_ID_RESTAURANTE_DESC, example = PLATO_ID_RESTAURANTE_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_LONG, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idRestaurante;

    @Schema(description = CANTIDAD_DESC, example = CANTIDAD_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_INTEGER, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCategoria;

    @Schema(hidden = true)
    private Long idPropietario;

}