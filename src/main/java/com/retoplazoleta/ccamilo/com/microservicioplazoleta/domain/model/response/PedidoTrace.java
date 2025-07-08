package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoTrace {
    private Long id;
    private Long idCliente;
    private LocalDate fecha;
    private EstadoPedido estado;
    private Long idChef;
    private Restaurante restaurante;
    private List<PedidoPlato> platos;
    private String correoCliente;
    private String correoEmpleado;
}
