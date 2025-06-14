package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.PedidoEntity}
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Long id;
    private Long idCliente;
    private LocalDate fecha;
    private EstadoPedido estado;
    private Long idChef;
    private Restaurante restaurante;

    private List<PedidoPlato> platos;
}
