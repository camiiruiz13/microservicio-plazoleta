package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;

public interface IPedidoHandler {

    void savePedido(PedidoDTO pedidoDTO);
    void updatePedido(Long idPedido, PedidoUpdateDTO pedidoDTO);

    PageResponseDTO<PedidoDTOResponse> findByEstadoAndRestauranteId(String estado, Long idRestaurante, Long idChef, int page, int pageSize);
}
