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

    @Schema(description = NOMBRE_DESC, example = NOMBRE_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = DIRECCION_DESC, example = DIRECCION_EXAMPLE, type =STRING_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;

    @Schema(description = CORREO_DESC, example = CORREO_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @Schema(description = NIT_DESC, example = NIT_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nit;

    @Schema(description = NIT_DESC, example = NIT_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefono;;

    private String urlLogo;

}
