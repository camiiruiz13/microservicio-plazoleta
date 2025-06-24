package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RefactorException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PedidoUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    private IApiClientPort apiClientPort;

    @Mock
    private IPedidoPersistencePort pedidoPersistence;

    @Mock
    private IPlatoPersistencePort platoPersistence;
    @InjectMocks
    private PedidoUseCase useCase;

    @Captor
    ArgumentCaptor<String> pinCaptor;


    @Test
    @Order(1)
    void savePedido_ok_procesaCorrectamente() {
        Pedido pedido = buildPedido();
        when(pedidoPersistence.clientFindPedidoProcess(1L)).thenReturn(false);
        when(platoPersistence.findById(1L)).thenReturn(new Plato());
        when(platoPersistence.findById(3L)).thenReturn(new Plato());
        when(platoPersistence.findById(4L)).thenReturn(new Plato());
        when(platoPersistence.existsPlatosOfRestaurant(List.of(1L, 3L, 4L), 1L)).thenReturn(false);
        when(pedidoPersistence.savePedido(any(Pedido.class))).thenReturn(pedido);
        useCase.savePedido(pedido);
        verify(pedidoPersistence).savePedido(pedido);
    }

    @Test
    @Order(2)
    void savePedido_clienteConPedidoPendiente_rechaza() {
        Pedido pedido = buildPedido();

        pedido.setIdCliente(42L);
        when(pedidoPersistence.clientFindPedidoProcess(42L)).thenReturn(true);

        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedido));
        assertEquals(PEDIDO_PROCESS.getMessage(), ex.getMessage());
    }

    @Test
    @Order(3)
    void savePedido_sinPlatos_rechaza() {

        Pedido pedido = buildPedido();
        pedido.setPlatos(null);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedido));
        assertEquals(PEDIDO_NO_EXITS.getMessage(), ex.getMessage());

        pedido.setPlatos(List.of());
        ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedido));
        assertEquals(PEDIDO_NO_EXITS.getMessage(), ex.getMessage());

    }

    @Test
    @Order(4)
    void savePedido_noEncuentraPlatos_rechaza() {

        Pedido pedido = buildPedido();
        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setIdPlato(100L);
        pedidoPlato.setCantidad(15);
        pedido.setPlatos(List.of(pedidoPlato));
        when(pedidoPersistence.clientFindPedidoProcess(1L)).thenReturn(false);
        when(platoPersistence.findById(100L)).thenReturn(null);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedido));
        assertEquals(ID_PLATO_PEDIDO_NULL.getMessage() + List.of(100L), ex.getMessage());



    }


private Pedido buildPedido() {

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Prueba test");
        restaurante.setDireccion("Direccion Test");
        restaurante.setIdPropietario(1L);
        restaurante.setNit("2563698");
        restaurante.setTelefono("+573005698325");
        restaurante.setUrlLogo("http://www.images.com");
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setIdCliente(1L);
        pedido.setRestaurante(restaurante);
        pedido.setPlatos(builPedidoPlatos());
        return pedido;


    }

    private List<PedidoPlato> builPedidoPlatos(){

        PedidoPlato pp1 = new PedidoPlato();
        pp1.setIdPlato(1L);
        pp1.setCantidad(15);

        PedidoPlato pp2 = new PedidoPlato();
        pp2.setIdPlato(3L);
        pp2.setCantidad(12);

        PedidoPlato pp3 = new PedidoPlato();
        pp3.setIdPlato(4L);
        pp3.setCantidad(12);

        List<PedidoPlato> platos = List.of(pp1, pp2, pp3);

        return platos;

    }

    private User buildValidUser() {
        User user = new User();
        user.setIdUsuario(1L);
        user.setNombre("Test");
        user.setApellido("Test");
        user.setNumeroDocumento("123456");
        user.setCelular("+573001112233");
        user.setCorreo("test@correo.com");
        return user;
    }



}
