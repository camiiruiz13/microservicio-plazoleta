package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util.SwaggerConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteDTO {

    @Schema(description = RESTAURANTE_NOMBRE_DESC, example = RESTAURANTE_NOMBRE_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = DIRECCION_DESC, example = DIRECCION_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;

    @Schema(description = CORREO_DESC, example = CORREO_EXAMPLE, type =STRING_TYPE,  requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @Schema(description = NIT_DESC, example = NIT_EXAMPLE, type =STRING_TYPE,  requiredMode = Schema.RequiredMode.REQUIRED)
    private String nit;

    @Schema(description = TELEFONO_DESC, example = TELEFONO_EXAMPLE, type =STRING_TYPE,  maxLength = 13,
            pattern = PATTERN, requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefono;;

    @Schema(description = IMAGE_DESC, example = IMAGE_EXAMPLE , type =STRING_TYPE,  requiredMode = Schema.RequiredMode.REQUIRED)
    private String urlLogo;

}
