package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoTraceDTOResponse;

import java.util.List;

public interface IPedidoHandler {

    void savePedido(PedidoDTO pedidoDTO);
    void asignarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO,String token);
    void notificarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO, String token);
    PageResponseDTO<PedidoDTOResponse> findByEstadoAndRestauranteId(String estado, Long idRestaurante,  int page, int pageSize);
    void entregarPedido(Long idPedido, PedidoDeliverDTO pedidoDTO,String token);
    void cancelarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO, String token);
    List<PedidoTraceDTOResponse> findPedidoByIdRestaurant(Long idRestaurante, Long idPropietario,String token);
}
