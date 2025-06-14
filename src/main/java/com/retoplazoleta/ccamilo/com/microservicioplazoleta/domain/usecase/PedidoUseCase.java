package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;

    private final IPlatoPersistencePort platoPersistencePort;


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

        List<Plato> platos = idsPlatos.stream()
                .map(id -> {
                    Plato plato = platoPersistencePort.findById(id);
                    if (plato == null) {
                        throw new PedidoValidationException(ID_PLATO_PEDIDO_NULL.getMessage() + id );
                    }
                    return plato;
                })
                .toList();

        if (platoPersistencePort.existsPlatosOfRestaurant(idsPlatos, idRestaurante)) {
            throw new PedidoValidationException(PEDIDO_PLATO_RESTAURANTE.getMessage());
        }

        Pedido pedidoGuardado = pedidoPersistencePort.savePedido(pedido);

        pedidoPlatos.forEach(pp -> pp.setIdPedido(pedidoGuardado.getId()));

        pedidoPersistencePort.savePedidoPlatos(pedidoPlatos, pedidoGuardado, platos);
    }
}
