package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.Role;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.RoleException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter.ApiAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.FIND_BY_CORREO_API;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.RESTAURANTE_ROLE_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiAdapterTest {

    @Mock
    private IGenericApiClient loginClient;

    @InjectMocks
    private ApiAdapter apiAdapter;

    @Test
    void idPropietario_CuandoModelEsNullYNoEsPropietario_NoLanzaExcepcion() {
        String correo = "user@email.com";
        String token = "token321";
        Long expectedId = 123L;

        Role role = new Role();
        role.setNombre("CLIENTE");

        User user = new User();
        user.setIdUsuario(expectedId);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        ReflectionTestUtils.setField(apiAdapter, "urlUsers", "http://test.com");

        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(token),
                eq(User.class))
        ).thenReturn(response);

        Long resultado = apiAdapter.idPropietario(correo, token, null);

        assertEquals(expectedId, resultado);
    }

    @Test
    void idPropietario_CuandoModelNoEsRestauranteYNoEsPropietario_NoLanzaExcepcion() {
        String correo = "otro@email.com";
        String token = "tokenXYZ";
        Long expectedId = 555L;

        Role role = new Role();
        role.setNombre("ADMIN");

        User user = new User();
        user.setIdUsuario(expectedId);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        ReflectionTestUtils.setField(apiAdapter, "urlUsers", "http://test.com");

        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(token),
                eq(User.class))
        ).thenReturn(response);

        Object otroModel = new Object();
        Long resultado = apiAdapter.idPropietario(correo, token, otroModel);

        assertEquals(expectedId, resultado);
    }

    @Test
    void idPropietario_CuandoModelEsRestauranteYNoEsPropietario_LanzaExcepcion() {
        String correo = "noowner@correo.com";
        String token = "token123";

        Role role = new Role();
        role.setNombre("CLIENTE");

        User user = new User();
        user.setIdUsuario(999L);
        user.setRol(role);

        GenericResponseDTO<User> response = new GenericResponseDTO<>();
        response.setObjectResponse(user);

        ReflectionTestUtils.setField(apiAdapter, "urlUsers", "http://test.com");

        String urlEsperada = "http://test.com" + FIND_BY_CORREO_API.getMessage().replace("{correo}", correo);

        when(loginClient.sendRequest(
                eq(urlEsperada),
                eq(HttpMethod.GET),
                isNull(),
                eq(token),
                eq(User.class))
        ).thenReturn(response);

        Object model = new com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante();

        RoleException ex = assertThrows(RoleException.class, () -> {
            apiAdapter.idPropietario(correo, token, model);
        });

        assertEquals(RESTAURANTE_ROLE_EXCEPTION.getMessage(), ex.getMessage());
    }


}
