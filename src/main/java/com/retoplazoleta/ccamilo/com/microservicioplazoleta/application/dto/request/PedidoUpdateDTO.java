package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util.SwaggerConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoUpdateDTO {

    @Schema(description = PLATO_ID_RESTAURANTE_DESC, example = PLATO_ID_RESTAURANTE_EXAMPLE, type =NUMBER_TYPE, format = FORMAT_LONG, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idRestaurante;

    @Schema(hidden = true)
    private Long idChef;

    @Schema(hidden = true)
    private String correoEmpleado;

    @Schema(hidden = true)
    private Long idCliente;

    @Schema(hidden = true)
    private String correoCliente;
}
