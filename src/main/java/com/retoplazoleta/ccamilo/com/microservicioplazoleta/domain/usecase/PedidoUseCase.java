package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPedidoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RefactorException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.request.TraceLog;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

        if (pedidoPersistencePort.clientFindPedidoProcess(idCliente)) {
            throw new PedidoValidationException(PEDIDO_PROCESS.getMessage());
        }

        if (pedido.getPlatos() == null || pedido.getPlatos().isEmpty()) {
            throw new PedidoValidationException(PEDIDO_NO_EXITS.getMessage());
        }


        List<Long> idsPlatos = pedidoPlatos.stream()
                .map(PedidoPlato::getIdPlato)
                .toList();

        List<Long> missingPlatoIds = idsPlatos.stream()
                .filter(id -> platoPersistencePort.findById(id) == null)
                .toList();
        if (!missingPlatoIds.isEmpty()) {
            throw new PedidoValidationException(ID_PLATO_PEDIDO_NULL.getMessage() + missingPlatoIds);
        }
        if (platoPersistencePort.existsPlatosOfRestaurant(idsPlatos, idRestaurante)) {
            throw new PedidoValidationException(PEDIDO_PLATO_RESTAURANTE.getMessage());
        }
        pedidoPersistencePort.savePedido(pedido);
    }

    @Override
    public void asignarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) {
        Pedido pedidoExistente = findById(idPedido);
        pedidoExistente.setIdChef(pedido.getIdChef());
        if (pedidoExistente.getEstado() != EstadoPedido.PENDIENTE)
            throw new PedidoValidationException(PEDIDO_ESTADO_PREPARACION.getMessage() + pedidoExistente.getEstado());
        User user = apiClientPort.idUser(pedidoExistente.getIdCliente(), token);
        buildTraceLog(pedidoExistente, correoEmpleado, EstadoPedido.EN_PREPARACION, user.getCorreo(), token);
        pedidoExistente.setEstado(EstadoPedido.EN_PREPARACION);
        pedidoPersistencePort.savePedido(pedidoExistente);
    }

    @Override
    public void notificarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) {
        Pedido pedidoExistente = findById(idPedido);
        if (!pedidoExistente.getIdChef().equals(pedido.getIdChef()))
            throw new PedidoValidationException(PEDIDO_PLATO_EMPLEADO_RESTAURANTE.getMessage() + pedido.getIdChef());
        if (pedidoExistente.getEstado() != EstadoPedido.EN_PREPARACION)
            throw new PedidoValidationException(PEDIDO_ESTADO_DIFERENTE.getMessage() + EstadoPedido.EN_PREPARACION + " " + pedidoExistente.getEstado());
        String pinSeguridad = crearPinSeguridad();
        User user = apiClientPort.idUser(pedidoExistente.getIdCliente(), token);
        apiClientPort.notificarUser(user.getCelular(), pinSeguridad, token);
        buildTraceLog(pedidoExistente, correoEmpleado, EstadoPedido.LISTO, user.getCorreo(), token);
        pedidoExistente.setPinSeguridad(pinSeguridad);
        pedidoExistente.setEstado(EstadoPedido.LISTO);
        pedidoPersistencePort.savePedido(pedidoExistente);
    }

    @Override
    public PageResponse<Pedido> findByEstadoAndRestauranteId(EstadoPedido estado, Long idRestaurante, int page, int pageSize) {

        PageResponse<Pedido> result = pedidoPersistencePort.findByEstadoAndRestauranteId(estado, idRestaurante, page, pageSize);

        if (result == null || result.getContent() == null || result.getContent().isEmpty()) {
            throw new RefactorException(PEDIDO_RESTAURANTE, idRestaurante);
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

    @Override
    public void entregarPedido(Long idPedido, String correoEmpleado, Pedido pedido, String token) {

        Pedido pedidoExistente = findById(idPedido);
        if (!pedidoExistente.getIdChef().equals(pedido.getIdChef()))
            throw new PedidoValidationException(PEDIDO_PLATO_EMPLEADO_RESTAURANTE.getMessage() + pedido.getIdChef());
        if (!pedidoExistente.getPinSeguridad().equals(pedido.getPinSeguridad()))
            throw new PedidoValidationException(CODIGO_PEDIDO.getMessage());
        if (pedidoExistente.getEstado() != EstadoPedido.LISTO)
            throw new PedidoValidationException(PEDIDO_ESTADO_DIFERENTE.getMessage() + pedidoExistente.getEstado());
        User user = apiClientPort.idUser(pedidoExistente.getIdCliente(), token);
        buildTraceLog(pedidoExistente, correoEmpleado, EstadoPedido.ENTREGADO, user.getCorreo(), token);
        pedidoExistente.setEstado(EstadoPedido.ENTREGADO);
        pedidoPersistencePort.savePedido(pedidoExistente);
    }

    @Override
    public void cancelarPedido(Long idPedido, Pedido pedido, String correoCliente, String token) {
        Pedido pedidoExistente = findById(idPedido);
        if (!pedidoExistente.getIdCliente().equals(pedido.getIdCliente()))
            throw new PedidoValidationException(PEDIDO_CANCELED.getMessage() + pedido.getIdCliente());
        if (pedidoExistente.getEstado() != EstadoPedido.PENDIENTE)
            throw new PedidoValidationException(PEDIDO_ESTADO_DIFERENTE_PENDIENTE.getMessage());
        User user = apiClientPort.idUser(pedidoExistente.getIdChef(), token);
        buildTraceLog(pedidoExistente, user.getCorreo(), EstadoPedido.CANCELADO, correoCliente, token);
        pedidoExistente.setEstado(EstadoPedido.CANCELADO);
        pedidoPersistencePort.savePedido(pedidoExistente);
    }

    @Override
    public List<PedidoTrace> findByIdRestaurant(Long idRestaurante, Long idPropietario,String token) {
        List<PedidoTrace> findByIdRestaurant = pedidoPersistencePort.findByIdRestaurant(idRestaurante);
        if (findByIdRestaurant == null)
            throw new RefactorException(NO_EXISTE_PEDIDO_RESTAURANTE, idRestaurante);
        Long propietarioRestaurante = findByIdRestaurant.get(0).getRestaurante().getIdPropietario();
        if (!idPropietario.equals(propietarioRestaurante)) {
            throw new RefactorException(PROPIETARIO_NO_PERTENECE, idPropietario);
        }
        List<Long> clientIds = findByIdRestaurant.stream()
                .map(PedidoTrace::getIdCliente)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<Long> chefIds = findByIdRestaurant.stream()
                .map(PedidoTrace::getIdChef)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        List<User> findUsersByIds = apiClientPort.fetchEmployeesAndClients(clientIds, chefIds, token);

        findByIdRestaurant.forEach(pedido -> {
            findUsersByIds.stream()
                    .filter(user -> user.getIdUsuario().equals(pedido.getIdCliente()))
                    .findFirst()
                    .ifPresent(user -> pedido.setCorreoCliente(user.getCorreo()));

            findUsersByIds.stream()
                    .filter(user -> user.getIdUsuario().equals(pedido.getIdChef()))
                    .findFirst()
                    .ifPresent(user -> pedido.setCorreoEmpleado(user.getCorreo()));
        });

        return findByIdRestaurant;
    }

    private String crearPinSeguridad() {
        int pin = (int) (Math.random() * 10_000);
        return String.format("%04d", pin);
    }

    private TraceLog buildTraceLog(Pedido p, String correoEmpleado, EstadoPedido nuevoEstado, String correoCliente, String token) {

        TraceLog traceLog = TraceLog.builder()
                .idPedido(p.getId())
                .idCliente(p.getIdCliente())
                .correoCliente(correoCliente)
                .fecha(LocalDateTime.now())
                .estadoAnterior(p.getEstado().name())
                .estadoNuevo(nuevoEstado.name())
                .idEmpleado(p.getIdChef() != null ? p.getIdChef() : 0L)
                .correoEmpleado(correoEmpleado != null ? correoEmpleado : "")
                .build();
        apiClientPort.crearTraza(traceLog, token);
        return traceLog;
    }
}
