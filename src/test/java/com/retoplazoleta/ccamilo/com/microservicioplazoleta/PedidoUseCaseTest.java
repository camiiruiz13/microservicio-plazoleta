package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PedidoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RefactorException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Pedido;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.PedidoPlato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.request.TraceLog;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PedidoTrace;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPedidoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PedidoUseCase;
import org.junit.jupiter.api.*;
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
import static org.mockito.Mockito.*;

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
    private ArgumentCaptor<Pedido> pedidoCaptor;


    private Pedido pedidoBase;
    private User userBase;
    private final String correoMock = "cliente@correo.com";
    private final String token = "dummyToken";

    @BeforeEach
    void setUp() {
        pedidoBase = buildPedido();
        pedidoBase.setEstado(EstadoPedido.PENDIENTE);
        userBase = buildValidUser();
    }

    @Test
    @Order(1)
    void savePedido_ok_procesaCorrectamente() {
        when(pedidoPersistence.clientFindPedidoProcess(1L)).thenReturn(false);
        when(platoPersistence.findById(1L)).thenReturn(new Plato());
        when(platoPersistence.findById(3L)).thenReturn(new Plato());
        when(platoPersistence.findById(4L)).thenReturn(new Plato());
        when(platoPersistence.existsPlatosOfRestaurant(List.of(1L, 3L, 4L), 1L)).thenReturn(false);
        when(pedidoPersistence.savePedido(any(Pedido.class))).thenReturn(pedidoBase);
        useCase.savePedido(pedidoBase);
        verify(pedidoPersistence).savePedido(pedidoBase);
    }

    @Test
    @Order(2)
    void savePedido_clienteConPedidoPendiente_rechaza() {

        pedidoBase.setIdCliente(42L);
        when(pedidoPersistence.clientFindPedidoProcess(42L)).thenReturn(true);

        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedidoBase));
        assertEquals(PEDIDO_PROCESS.getMessage(), ex.getMessage());
    }

    @Test
    @Order(3)
    void savePedido_sinPlatos_rechaza() {


        pedidoBase.setPlatos(null);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedidoBase));
        assertEquals(PEDIDO_NO_EXITS.getMessage(), ex.getMessage());

        pedidoBase.setPlatos(List.of());
        ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedidoBase));
        assertEquals(PEDIDO_NO_EXITS.getMessage(), ex.getMessage());

    }

    @Test
    @Order(4)
    void savePedido_noEncuentraPlatos_rechaza() {


        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setIdPlato(100L);
        pedidoPlato.setCantidad(15);
        pedidoBase.setPlatos(List.of(pedidoPlato));
        when(pedidoPersistence.clientFindPedidoProcess(1L)).thenReturn(false);
        when(platoPersistence.findById(100L)).thenReturn(null);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedidoBase));
        assertEquals(ID_PLATO_PEDIDO_NULL.getMessage() + List.of(100L), ex.getMessage());

    }

    @Test
    @Order(5)
    void savePedido_plato_noPertenece_restahrante() {

        when(pedidoPersistence.clientFindPedidoProcess(1L)).thenReturn(false);
        when(platoPersistence.findById(1L)).thenReturn(new Plato());
        when(platoPersistence.findById(3L)).thenReturn(new Plato());
        when(platoPersistence.findById(4L)).thenReturn(new Plato());
        when(platoPersistence.existsPlatosOfRestaurant(List.of(1L, 3L, 4L), 1L)).thenReturn(true);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.savePedido(pedidoBase));
        assertEquals(PEDIDO_PLATO_RESTAURANTE.getMessage(), ex.getMessage());

    }

    @Test
    @Order(6)
    void findByEstadoAndRestauranteId_pendiente_conPedidos_deberiaRetornarPagina() {
        Long idRestaurante = 1L;
        int page = 0;
        int pageSize = 10;

        List<Pedido> pedidos = List.of(new Pedido());
        PageResponse<Pedido> response = PageResponse.<Pedido>builder()
                .content(pedidos)
                .build();

        when(pedidoPersistence.findByEstadoAndRestauranteId(
                eq(PENDIENTE), eq(idRestaurante), eq(page), eq(pageSize))
        ).thenReturn(response);

        PageResponse<Pedido> result = useCase.findByEstadoAndRestauranteId(
                PENDIENTE, idRestaurante, page, pageSize
        );

        assertEquals(pedidos, result.getContent());
    }

    @Test
    @Order(7)
    void findByEstadoAndRestauranteId_pendiente_sinPedidos_deberiaLanzarExcepcion() {
        Long idRestaurante = 1L;
        when(pedidoPersistence.findByEstadoAndRestauranteId(
                eq(PENDIENTE), eq(idRestaurante), anyInt(), anyInt())
        ).thenReturn(PageResponse.<Pedido>builder().content(Collections.emptyList()).build());

        RefactorException exception = assertThrows(RefactorException.class, () ->
                useCase.findByEstadoAndRestauranteId(PENDIENTE, idRestaurante, 0, 10)
        );
        assertTrue(exception.getMessage().contains(PEDIDO_RESTAURANTE.getMessage()));
    }


    @Test
    @Order(8)
    void findByIdPedido_Retorna_pedido() {
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        Pedido result = useCase.findById(1L);
        assertEquals(pedidoBase, result);
    }

    @Test
    @Order(9)
    void findByIdPedido_Retorna_null() {
        when(pedidoPersistence.findById(1L)).thenReturn(null);
        RefactorException ex = assertThrows(RefactorException.class,
                () -> useCase.findById(1L));
        assertEquals(ID_PEDIDO_NULL.getMessage() + 1L, ex.getMessage());
    }

    @Test
    @Order(10)
    void asignarPedido_ok() {
        Long idPedido = 1L;
        Long idChef = 1L;
        pedidoBase.setIdChef(idChef);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        when(apiClientPort.idUser(pedidoBase.getIdCliente(), token)).thenReturn(userBase);
        useCase.asignarPedido(idPedido, correoMock, pedidoBase, token);
        verify(pedidoPersistence).savePedido(pedidoBase);
    }

    @Test
    @Order(11)
    void asignarPedido_estado_diferente() {
        Long idPedido = 1L;
        Long idChef = 1L;
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        pedidoBase.setEstado(LISTO);
        pedidoBase.setIdChef(idChef);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.asignarPedido(idPedido, correoMock, pedidoBase, token));
    }


    @Test
    @Order(12)
    void notificarPedido_ok() {
        Long idPedido = 1L;
        Long idChef = 1L;
        pedidoBase.setIdChef(idChef);
        pedidoBase.setPinSeguridad("4070");
        pedidoBase.setEstado(EN_PREPARACION);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        when(apiClientPort.idUser(pedidoBase.getIdCliente(), token)).thenReturn(userBase);
        useCase.notificarPedido(idPedido, correoMock, pedidoBase, token);
        verify(pedidoPersistence).savePedido(pedidoBase);
    }

    @Test
    @Order(13)
    void notificarPedido_idCliente_noPertenece() {
        Long idPedido = 1L;
        pedidoBase.setIdChef(100L);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.notificarPedido(idPedido, correoMock, pedidoBase, token));

    }

    @Test
    @Order(14)
    void testCrearPinSeguridadViaReflection() throws Exception {

        String pin = TestUtil.invokePrivateMethod(
                useCase,
                "crearPinSeguridad",
                String.class,
                new Class<?>[]{}
        );

        assertEquals(4, pin.length());

    }

    @Test
    @Order(15)
    void entregarPedido_ok() {
        Long idPedido = 1L;
        Long idChef = 1L;
        pedidoBase.setIdChef(idChef);
        pedidoBase.setPinSeguridad("4070");
        pedidoBase.setEstado(LISTO);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        when(apiClientPort.idUser(pedidoBase.getIdCliente(), token)).thenReturn(userBase);
        useCase.entregarPedido(idPedido, correoMock, pedidoBase, token);
        verify(pedidoPersistence).savePedido(pedidoBase);
    }

    @Test
    @Order(16)
    void entregarPedido_idChef_noPertenece() {
        Long idPedido = 1L;
        Pedido pedido = buildPedido();
        Pedido pedidoExistente = buildPedido();
        pedidoExistente.setIdChef(100L);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoExistente);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.entregarPedido(idPedido, correoMock, pedido, token));
        assertEquals(PEDIDO_PLATO_EMPLEADO_RESTAURANTE.getMessage() + pedido.getIdChef(), ex.getMessage());
    }

    @Test
    @Order(17)
    void entregarPedido_codigo_error() {
        Long idPedido = 1L;
        pedidoBase.setIdChef(1L);
        pedidoBase.setPinSeguridad("4070");
        Pedido pedido = new Pedido();
        pedido.setPinSeguridad("5000");
        pedido.setIdChef(1L);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.entregarPedido(idPedido, correoMock, pedido, token));
        assertEquals(CODIGO_PEDIDO.getMessage(), ex.getMessage());
    }

    @Order(18)
    @Test
    void cancelarPedido_ok() {
        Long idPedido = 1L;

        pedidoBase.setEstado(EstadoPedido.PENDIENTE);
        pedidoBase.setIdChef(1L);
        userBase.setIdUsuario(1L);

        when(pedidoPersistence.findById(idPedido)).thenReturn(pedidoBase);
        when(apiClientPort.idUser(1L, token)).thenReturn(userBase);

        useCase.cancelarPedido(idPedido, pedidoBase, correoMock, token);
        verify(pedidoPersistence).savePedido(pedidoCaptor.capture());
    }


    @Test
    @Order(19)
    void cancelarPedido_cliente_no_pertenece() {
        Long idPedido = 1L;
        pedidoBase.setEstado(CANCELADO);
        pedidoBase.setIdChef(1L);
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setIdCliente(10L);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.cancelarPedido(idPedido, pedidoExistente, correoMock, token));
        assertEquals(PEDIDO_CANCELED.getMessage() + pedidoExistente.getIdCliente(), ex.getMessage());
    }

    @Test
    @Order(20)
    void cancelarPedido_estadoDistinto_lanzaExcepcion() {
        Long idPedido = 1L;
        pedidoBase.setEstado(LISTO);
        pedidoBase.setIdCliente(1L);
        when(pedidoPersistence.findById(idPedido)).thenReturn(pedidoBase);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.cancelarPedido(idPedido, pedidoBase, correoMock, token));
        assertEquals(PEDIDO_ESTADO_DIFERENTE_PENDIENTE.getMessage(), ex.getMessage());
    }



    @Test
    @Order(21)
    void buildTraceLog() throws Exception {

        String pin = TestUtil.invokePrivateMethod(
                useCase,
                "crearPinSeguridad",
                String.class,
                new Class<?>[]{}
        );

        assertEquals(4, pin.length());

    }

    @Test
    @Order(22)
    void buildTraceLog_conChefYCorreoEmpleado() throws Exception {
        pedidoBase.setIdChef(3L);
        TraceLog traceLog = TestUtil.invokePrivateMethod(
                useCase,
                "buildTraceLog",
                TraceLog.class,
                new Class[]{Pedido.class, String.class, EstadoPedido.class, String.class, String.class},
                pedidoBase, correoMock, EstadoPedido.LISTO, correoMock, token
        );

        assertNotNull(traceLog);
    }

    @Test
    @Order(23)
    void notificarPedido_idChef_noPertenece() {
        Long idPedido = 1L;
        pedidoBase.setIdChef(100L);
        Pedido pedio = new Pedido();
        pedidoBase.setId(1L);
        pedio.setIdChef(1L);
        when(pedidoPersistence.findById(1L)).thenReturn(pedidoBase);
        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.notificarPedido(idPedido, correoMock, pedio, token));

    }


    @Test
    @Order(24)
    void buildTraceLog_sinChefYCorreoEmpleadoNull() throws Exception {
        pedidoBase.setIdChef(null);


        TraceLog traceLog = TestUtil.invokePrivateMethod(
                useCase,
                "buildTraceLog",
                TraceLog.class,
                new Class[]{Pedido.class, String.class, EstadoPedido.class, String.class, String.class},
                pedidoBase, null, EstadoPedido.CANCELADO, correoMock, token
        );

        assertEquals(0L, traceLog.getIdEmpleado());
    }

    @Test
    @Order(25)
    void cancelarPedido_estadoNoPendiente_lanzaExcepcion() {
        Long idPedido = 1L;

        Pedido pedidoPersistido = buildPedido();
        pedidoPersistido.setEstado(EstadoPedido.LISTO);
        pedidoPersistido.setIdCliente(1L);
        pedidoPersistido.setIdChef(1L);

        pedidoBase.setIdCliente(1L);
        pedidoBase.setEstado(EstadoPedido.LISTO);

        when(pedidoPersistence.findById(idPedido)).thenReturn(pedidoPersistido);

        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.cancelarPedido(idPedido, pedidoBase, correoMock, token));

        assertEquals(PEDIDO_ESTADO_DIFERENTE_PENDIENTE.getMessage(), ex.getMessage());
    }


    @Test
    @Order(26)
    void findByIdRestaurant_ok_asignaCorreos() {
        Long idRestaurante = 1L;
        Long idPropietario = 10L;
        String token = "Bearer xyz";

        PedidoTrace pedido = new PedidoTrace();
        pedido.setIdCliente(101L);
        pedido.setIdChef(202L);

        Restaurante restaurante = new Restaurante();
        restaurante.setIdPropietario(idPropietario);
        pedido.setRestaurante(restaurante);

        List<PedidoTrace> pedidos = List.of(pedido);

        User cliente = new User();
        cliente.setIdUsuario(101L);
        cliente.setCorreo("cliente@correo.com");

        User chef = new User();
        chef.setIdUsuario(202L);
        chef.setCorreo("chef@correo.com");

        List<User> usuarios = List.of(cliente, chef);

        when(pedidoPersistence.findByIdRestaurant(idRestaurante)).thenReturn(pedidos);
        when(apiClientPort.fetchEmployeesAndClients(List.of(101L), List.of(202L), token)).thenReturn(usuarios);

        List<PedidoTrace> result = useCase.findByIdRestaurant(idRestaurante, idPropietario, token);
        assertEquals(1, result.size());

    }

    @Test
    @Order(27)
    void findByIdRestaurant_null_lanzaExcepcion() {
        Long idRestaurante = 1L;
        Long idPropietario = 10L;
        String token = "Bearer xyz";

        when(pedidoPersistence.findByIdRestaurant(idRestaurante)).thenReturn(null);

        RefactorException ex = assertThrows(RefactorException.class,
                () -> useCase.findByIdRestaurant(idRestaurante, idPropietario, token));

        assertEquals(NO_EXISTE_PEDIDO_RESTAURANTE.getMessage() + idRestaurante, ex.getMessage());
    }
    @Test
    @Order(28)
    void findByIdRestaurant_propietarioInvalido_lanzaExcepcion() {
        Long idRestaurante = 1L;
        Long idPropietario = 10L;
        Long propietarioReal = 99L;

        PedidoTrace pedido = new PedidoTrace();
        Restaurante restaurante = new Restaurante();
        restaurante.setIdPropietario(propietarioReal);
        pedido.setRestaurante(restaurante);

        List<PedidoTrace> pedidos = List.of(pedido);

        when(pedidoPersistence.findByIdRestaurant(idRestaurante)).thenReturn(pedidos);

        RefactorException ex = assertThrows(RefactorException.class,
                () -> useCase.findByIdRestaurant(idRestaurante, idPropietario, "Bearer xyz"));

        assertEquals(PROPIETARIO_NO_PERTENECE.getMessage() + idPropietario, ex.getMessage());
    }

    @Test
    @Order(30)
    void entregarPedido_estadoNoListo_lanzaExcepcion() {
        Long idPedido = 1L;

        Pedido pedidoPersistido = buildPedido();
        pedidoPersistido.setEstado(PENDIENTE);
        pedidoPersistido.setIdCliente(1L);
        pedidoPersistido.setIdChef(1L);
        pedidoPersistido.setPinSeguridad("4070");

        pedidoBase.setIdCliente(1L);
        pedidoBase.setEstado(LISTO);
        pedidoBase.setPinSeguridad("4070");
        pedidoBase.setIdChef(1L);

        when(pedidoPersistence.findById(idPedido)).thenReturn(pedidoPersistido);

        PedidoValidationException ex = assertThrows(PedidoValidationException.class,
                () -> useCase.entregarPedido(idPedido, correoMock, pedidoBase,  token));

        assertEquals(PEDIDO_ESTADO_DIFERENTE.getMessage() + pedidoPersistido.getEstado(), ex.getMessage());
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

    private List<PedidoPlato> builPedidoPlatos() {

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
