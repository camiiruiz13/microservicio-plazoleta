package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util.SwaggerConstants.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util.SwaggerConstants.FORMAT_LONG;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPlatoDTO {
    @Schema(description = PLATO_ID_DESC, example = PLATO_ID_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_LONG, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idPlato;

    @Schema(description = PLATO_ID_DESC, example = PLATO_ID_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_LONG, requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidad;
}
