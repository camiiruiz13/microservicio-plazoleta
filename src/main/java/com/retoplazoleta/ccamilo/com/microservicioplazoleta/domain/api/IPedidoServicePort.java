package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;

import java.util.List;

public interface IPedidoServicePort {

    void savePedido(Pedido pedido);
    void asignarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token);
    void notificarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) ;
    PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante, int page, int pageSize);
    Pedido findById(Long id);
    void entregarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token);
    void cancelarPedido(Long idPedido,  Pedido pedido, String correoCliente, String token);
    List<PedidoTrace> findByIdRestaurant(Long idRestaurante, Long idPropietario,String token);
}
