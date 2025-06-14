package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPlatoDTOResponse {

    private Long idPedido;
    private Long idPlato;
    private Integer cantidad;

}