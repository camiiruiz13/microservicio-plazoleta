package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RefactorException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IApiClientPort apiClientPort;

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
                        throw new PedidoValidationException(ID_PLATO_PEDIDO_NULL.getMessage() + id);
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

    @Override
    public void updatePedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) {

        Pedido pedidoExistente = findById(idPedido);

        EstadoPedido estadoActual = pedidoExistente.getEstado();
        EstadoPedido nuevoEstado = pedido.getEstado();

        if (estadoActual == EstadoPedido.PENDIENTE && pedidoExistente.getIdChef() == null) {
            pedidoExistente.setIdChef(pedido.getIdChef());
            pedidoExistente.setEstado(nuevoEstado);
        } else if (pedidoExistente.getIdChef() == null) {
            throw new RefactorException(EMPLEADO_PLATO_RESTAURANTE, pedido.getIdChef());
        } else {
            if (!pedidoExistente.getIdChef().equals(pedido.getIdChef()))
                throw new RefactorException(PEDIDO_PLATO_EMPLEADO_RESTAURANTE, pedido.getIdChef());
            if (nuevoEstado == EstadoPedido.LISTO) {
                String pinSeguridad = crearPinSeguridad();
                User user =  apiClientPort.findByIdCUser(pedidoExistente.getIdCliente(), token);
                apiClientPort.notificarUser(user.getCelular(),pinSeguridad,token);
                pedidoExistente.setPinSeguridad(pinSeguridad);
            }
            pedidoExistente.setEstado(nuevoEstado);

        }
        pedidoPersistencePort.savePedido(pedidoExistente);
    }

    @Override
    public PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante, Long idChef, int page, int pageSize) {

        PageResponse<Pedido> result = null;

        if (estado == EstadoPedido.PENDIENTE) {

            result = pedidoPersistencePort.findByEstadoAndRestauranteId(estado, idRestaurante, page, pageSize);

            if (result == null || result.getContent() == null || result.getContent().isEmpty()) {
                throw new RefactorException(PEDIDO_RESTAURANTE, idRestaurante);
            }

        } else {
            result = pedidoPersistencePort.findByEstadoAndRestauranteIdChef(estado, idRestaurante, idChef, page, pageSize);

            if (result == null || result.getContent() == null || result.getContent().isEmpty()) {
                throw new RefactorException(USER_NOT_RESTAURANT, idChef);
            }

        }
        return result;
    }

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = pedidoPersistencePort.findById(id);
        if (pedido == null)
            throw new RefactorException(ID_PEDIDO_NULL, id);
        return pedido;
    }

    private String crearPinSeguridad() {
        int pin = (int) (Math.random() * 10_000);
        return String.format("%04d", pin);
    }

}
