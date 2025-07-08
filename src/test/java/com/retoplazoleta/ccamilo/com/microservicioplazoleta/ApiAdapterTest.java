package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.request.TraceLog;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.Role;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.RoleException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter.ApiAdapter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.RESTAURANTE_ROLE_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ApiAdapterTest {

    @Mock
    private IGenericApiClient loginClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ApiAdapter apiAdapter;

    private static final String URL = "http://test.com";
    private static final String TOKEN = "Bearer test";

    @BeforeEach
    void setUp() {
        apiAdapter = new ApiAdapter(loginClient, objectMapper);
        ReflectionTestUtils.setField(apiAdapter, "urlUsers", URL);
        ReflectionTestUtils.setField(apiAdapter, "urlNotificaciones", "http://test.com");
    }


    @Test
    @Order(1)
    void idPropietario_SinModel_LlamaSobrecargaCorrectamente() {
        String correo = "test@correo.com";
      
        Long expectedId = 99L;

        Role role = new Role();
        role.setNombre("CLIENTE");

        User user = new User();
        user.setIdUsuario(expectedId);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(User.class))
        ).thenReturn(response);


        Long resultado = apiAdapter.idPropietario(correo, TOKEN);

        assertEquals(expectedId, resultado);
    }


    @Test
    @Order(2)
    void idPropietario_CuandoModelEsNullYNoEsPropietario_NoLanzaExcepcion() {
        String correo = "user@email.com";
        Long expectedId = 123L;

        Role role = new Role();
        role.setNombre("CLIENTE");

        User user = new User();
        user.setIdUsuario(expectedId);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);


        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(User.class))
        ).thenReturn(response);

        Long resultado = apiAdapter.idPropietario(correo, TOKEN, null);

        assertEquals(expectedId, resultado);
    }

    @Test
    @Order(3)
    void idPropietario_CuandoModelNoEsRestauranteYNoEsPropietario_NoLanzaExcepcion() {
        String correo = "otro@email.com";
        Long expectedId = 555L;

        Role role = new Role();
        role.setNombre("ADMIN");

        User user = new User();
        user.setIdUsuario(expectedId);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(User.class))
        ).thenReturn(response);

        Object otroModel = new Object();
        Long resultado = apiAdapter.idPropietario(correo, TOKEN, otroModel);

        assertEquals(expectedId, resultado);
    }

    @Test
    @Order(4)
    void idPropietario_CuandoModelEsRestauranteYNoEsPropietario_LanzaExcepcion() {
        String correo = "noowner@correo.com";
        Role role = new Role();
        role.setNombre("CLIENTE");

        User user = new User();
        user.setIdUsuario(999L);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(User.class))
        ).thenReturn(response);

        Object model = new com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante();

        RoleException ex = assertThrows(RoleException.class, () -> {
            apiAdapter.idPropietario(correo, TOKEN, model);
        });

        assertEquals(RESTAURANTE_ROLE_EXCEPTION.getMessage(), ex.getMessage());
    }

    @Test
    @Order(5)
    void findy_By_User() {

        String token = "token321";
        Long idUser = 1L;

        User user = buildValidUser();

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        String urlEsperada = "http://test.com" + FIND_BY_ID_API.getMessage().replace("{id}", idUser.toString());

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(token),
                eq(User.class))
        ).thenReturn(response);

        User resultado = apiAdapter.idUser(idUser, token);

        assertEquals(user, resultado);
    }

    @Test
    @Order(6)
    void notificate_user_response() {
        String celular = "+57123456789";
        String codigo = "codigo example";
      
        String urlEsperada = "http://test.com" +
                SEND_NOTIFICATION_ID_USER.getMessage()
                        .replace("{destinatario}", celular)
                        .replace("{mensaje}", codigo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(Object.class))
        ).thenReturn(GenericResponseDTO.builder().build());

        apiAdapter.notificarUser(celular, codigo, TOKEN);

        verify(loginClient).sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(TOKEN),
                eq(Object.class)
        );
    }

    @Test
    @Order(7)
    void create_trace_response() {
      
        ReflectionTestUtils.setField(apiAdapter, "urlTrazabilidad", "http://test.com");
        String urlEsperada = "http://test.com" + CREATE_TRACE.getMessage();
        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.POST),
                any(GenericRequest.class),
                eq(TOKEN),
                eq(Void.class)
        )).thenReturn(GenericResponseDTO.<Void>builder().build());
        apiAdapter.crearTraza(TraceLog.builder().build(), TOKEN);
        verify(loginClient).sendRequest(eq(urlEsperada),
                eq(HttpMethod.POST),
                any(GenericRequest.class),
                eq(TOKEN),
                eq(Void.class));
    }

    @Test
    @Order(8)
    void fetchEmployeesAndClients_returnsUserList() {

        List<Long> clientIds = List.of(1L);
        List<Long> chefIds = List.of(2L);

        Map<String, Object> userMap1 = Map.of("idUsuario", 1L, "correo", "cliente@correo.com");
        Map<String, Object> userMap2 = Map.of("idUsuario", 2L, "correo", "chef@correo.com");

        GenericResponseDTO<Object> response = new GenericResponseDTO<>();
        response.setObjectResponse(List.of(userMap1, userMap2));

        when(loginClient.sendRequest(anyString(), eq(HttpMethod.POST), any(), eq(TOKEN), eq(Object.class)))
                .thenReturn(response);

        List<User> result = apiAdapter.fetchEmployeesAndClients(clientIds, chefIds, TOKEN);

        assertEquals(2, result.size());
    }

    @Test
    @Order(9)
    void fetchEmployeesAndClients_responseIsNotList_returnsEmptyList() {
        List<Long> clientIds = List.of(1L);
        List<Long> chefIds = List.of(2L);

        GenericResponseDTO<Object> response = new GenericResponseDTO<>();
        response.setObjectResponse(Map.of("unexpected", "value"));

        when(loginClient.sendRequest(anyString(), eq(HttpMethod.POST), any(), eq(TOKEN), eq(Object.class)))
                .thenReturn(response);

        List<User> result = apiAdapter.fetchEmployeesAndClients(clientIds, chefIds, TOKEN);

        assertTrue(result.isEmpty());
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
