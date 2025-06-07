package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Long id;
    private Long idCliente;
    private String fecha;
    private EstadoPedido estado;
    private Long idChef;
    private Restaurante restaurante;
}
