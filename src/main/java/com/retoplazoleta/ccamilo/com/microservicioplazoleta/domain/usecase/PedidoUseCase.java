package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;


    @Override
    public void savePedido(Pedido pedido) {
        Long idRestaurante = pedido.getRestaurante().getId();
        Long idCliente = pedido.getIdCliente();

        List<PedidoPlato> pedidoPlatos = pedido.getPlatos();

        if (pedidoPlatos == null || pedidoPlatos.isEmpty()) {
            throw new PedidoValidationException(PEDIDO_NO_EXITS.getMessage());
        }

        if (pedidoPersistencePort.clientFindPedidoProcess(idCliente)) {
            throw new PedidoValidationException(PEDIDO_PROCESS.getMessage());
        }

        List<Long> idsPlatos = pedidoPlatos.stream()
                .map(PedidoPlato::getIdPlato)
                .toList();

        if (pedidoPersistencePort.existsPlatosOfRestaurant(idsPlatos, idRestaurante)) {
            throw new PedidoValidationException(PEDIDO_PLATO_RESTAURANTE.getMessage());
        }

        Pedido pedidoGuardado = pedidoPersistencePort.savePedido(pedido);

        pedidoPlatos.forEach(pp -> pp.setIdPedido(pedidoGuardado.getId()));

        pedidoPersistencePort.savePedidoPlatos(pedidoPlatos);
    }
}
