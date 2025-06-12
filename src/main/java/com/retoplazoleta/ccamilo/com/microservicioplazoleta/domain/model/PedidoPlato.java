package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPlato {

    private Long idPedido;
    private Long idPlato;
    private Integer cantidad;

}