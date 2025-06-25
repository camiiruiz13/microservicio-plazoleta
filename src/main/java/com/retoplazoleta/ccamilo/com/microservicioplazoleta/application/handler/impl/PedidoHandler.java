package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IPedidoResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PageResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoRequestMapper pedidoRequestMapper;
    private final IPedidoResponseMapper pedidoResponseMapper;


    @Override
    public void savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRequestMapper.toPedido(pedidoDTO);
        pedidoServicePort.savePedido(pedido);
    }

    @Override
    public void asignarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO){
        Pedido pedido = pedidoRequestMapper.toPedidoUpdate(pedidoDTO);
        pedidoServicePort.asignarPedido(idPedido, pedido);
    }

    @Override
    public void notificarPedido(Long idPedido, PedidoUpdateDTO pedidoDTO, String token) {
        Pedido pedido = pedidoRequestMapper.toPedidoUpdate(pedidoDTO);
        pedidoServicePort.notificarPedido(idPedido,pedidoDTO.getCorreoEmpleado(), pedido, token);
    }

    @Override
    public PageResponseDTO<PedidoDTOResponse> findByEstadoAndRestauranteId(String estado, Long idRestaurante, int page, int pageSize) {
        return PageResponseMapper.toResponseDTO(
                pedidoServicePort.findByEstadoAndRestauranteId(EstadoPedido.valueOf(estado), idRestaurante,page, pageSize),
                pedidoResponseMapper::toResponse

        );
    }
}
