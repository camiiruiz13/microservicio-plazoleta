package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;

public interface IPedidoHandler {

    void savePedido(PedidoDTO pedidoDTO);
    void asignarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO);
    void notificarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO, String token);

    PageResponseDTO<PedidoDTOResponse> findByEstadoAndRestauranteId(String estado, Long idRestaurante,  int page, int pageSize);
    void entregarPedido(Long idPedido, PedidoDeliverDTO pedidoDTO);
}
