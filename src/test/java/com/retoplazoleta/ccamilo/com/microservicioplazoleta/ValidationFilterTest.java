package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RoleDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.UserDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.auth.ValidationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_VENCIDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ValidationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private IGenericApiClient loginClient;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    private final String correo = "test@correo.com";
    private final String jwt = JWT.create()
            .withSubject(correo)
            .withExpiresAt(new Date(System.currentTimeMillis() + 10000))
            .sign(Algorithm.HMAC256("secret"));

    private ValidationFilter buildFilter() throws Exception {
        ValidationFilter filter = new ValidationFilter(authenticationManager, loginClient);
        Field urlUsers = ValidationFilter.class.getDeclaredField("urlUsers");
        urlUsers.setAccessible(true);
        urlUsers.set(filter, "http://localhost:8080/user");
        return filter;
    }

    @Test
    @Order(1)
    void skipsIfHeaderNull() throws Exception {
        ValidationFilter filter = buildFilter();
        when(request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION)).thenReturn(null);

        TestUtil.invokePrivateMethod(
                filter,
                "doFilterInternal",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class},
                request, response, chain
        );

        verify(chain).doFilter(request, response);
    }


    @Test
    @Order(2)
    void skipsIfHeaderWithoutBearer() throws Exception {
        ValidationFilter filter = buildFilter();
        when(request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION)).thenReturn("XYZ123");

        TestUtil.invokePrivateMethod(
                filter,
                "doFilterInternal",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class},
                request, response, chain
        );

        verify(chain).doFilter(request, response);
    }

    @Test
    @Order(3)
    void rejectsExpiredToken() throws Exception {
        String expired = JWT.create()
                .withSubject(correo)
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .sign(Algorithm.HMAC256("secret"));

        ValidationFilter filter = buildFilter();
        when(request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION)).thenReturn(TokenJwtConfig.PREFIX_TOKEN + expired);

        StringWriter output = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(output));

        TestUtil.invokePrivateMethod(
                filter,
                "doFilterInternal",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class},
                request, response, chain
        );

        assertTrue(output.toString().contains(TOKEN_VENCIDO.getMessage()));
        verify(response).setStatus(401);
    }

    @Test
    @Order(4)
    void authenticatesWithValidToken() throws Exception {
        ValidationFilter filter = buildFilter();

        RoleDTO rol = new RoleDTO();
        rol.setNombre("ADMIN");

        UserDTOResponse user = new UserDTOResponse();
        user.setIdUsuario(1L);
        user.setCorreo(correo);
        user.setRol(rol);

        GenericResponseDTO<UserDTOResponse> apiResponse = new GenericResponseDTO<>();
        apiResponse.setObjectResponse(user);

        when(request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION))
                .thenReturn(TokenJwtConfig.PREFIX_TOKEN + jwt);

        when(loginClient.sendRequest(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                anyString(),
                eq(UserDTOResponse.class)
        )).thenAnswer(invocation -> {
            UserDTOResponse userDTO = new UserDTOResponse();
            userDTO.setIdUsuario(1L);
            userDTO.setCorreo(correo);
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setNombre("ADMIN");
            userDTO.setRol(roleDTO);

            GenericResponseDTO<UserDTOResponse> responseDTO = new GenericResponseDTO<>();
            responseDTO.setObjectResponse(userDTO);
            return responseDTO;
        });

        TestUtil.invokePrivateMethod(
                filter,
                "doFilterInternal",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class},
                request, response, chain
        );

        verify(chain).doFilter(request, response);
    }

    @Test
    @Order(5)
    void handlesExceptionInValidation() throws Exception {
        ValidationFilter filter = buildFilter();

        when(request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION))
                .thenReturn(TokenJwtConfig.PREFIX_TOKEN + jwt);


        when(loginClient.sendRequest(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                anyString(),
                eq(UserDTOResponse.class)
        )).thenThrow(new RuntimeException("Error personalizado"));


        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));


        TestUtil.invokePrivateMethod(
                filter,
                "doFilterInternal",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class},
                request, response, chain
        );

        // Validaciones
        String result = writer.toString();
        assertTrue(result.contains("Token inválido:"), "Debe contener el prefijo del mensaje");
        assertTrue(result.contains("Error personalizado"), "Debe contener el mensaje de la excepción");
        verify(response).setStatus(401);
    }

    @Test
    @Order(6)
    void testPrivateIsTokenExpired() throws Exception {
        ValidationFilter filter = buildFilter();

        Date expired = new Date(System.currentTimeMillis() - 1000);
        Date valid = new Date(System.currentTimeMillis() + 1000);

        boolean expiredResult = TestUtil.invokePrivateMethod(filter, "isTokenExpired", Boolean.class, new Class[]{Date.class}, expired);
        boolean validResult = TestUtil.invokePrivateMethod(filter, "isTokenExpired", Boolean.class, new Class[]{Date.class}, valid);

        assertTrue(expiredResult);
        assertFalse(validResult);
    }




}
