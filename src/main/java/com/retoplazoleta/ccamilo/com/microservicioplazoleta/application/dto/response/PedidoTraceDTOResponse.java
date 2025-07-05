package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato}
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoTraceDTOResponse {

    private Long idPedido;
    private Long idCliente;
    private String fecha;
    private String estado;
    private Long idChef;
    private RestauranteDTOResponse restaurante;
    private List<PedidoPlatoDTOResponse> platos;
    private String correoCliente;
    private String correoEmpleado;
}
