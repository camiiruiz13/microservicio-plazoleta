package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller.PedidoController;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoDeliverRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoUpdateRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private IPedidoHandler pedidoHandler;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PedidoController controller;

    private final String token = "dummyToken";

    @Test
    @Order(1)
    void crearPedido_Retorna201_CuandoRequestEsValida() {


        AuthenticatedUser user = new AuthenticatedUser(
                "10", "carlos@mail.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );


        PedidoDTO dto = new PedidoDTO();
        dto.setIdCliente(Long.valueOf(user.getIdUser()));
        PedidoRequest request = new PedidoRequest();
        request.setRequest(dto);

        ResponseEntity<GenericResponseDTO<Void>> response = controller.crearPedido(request, user);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @Order(2)
    void testListarPedidosRestaurante_Returns200() {
        String estado = "PENDIENTE";
        Long idRestaurante = 1L;
        int page = 0;
        int pageSize = 5;

        AuthenticatedUser user = new AuthenticatedUser(
                "1", "carlos@mail.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_EMPLEADO"))
        );


        PageResponseDTO<PedidoDTOResponse> pageResponse = PageResponseDTO.<PedidoDTOResponse>builder()
                .content(List.of(new PedidoDTOResponse()))
                .totalPages(1)
                .totalElements(1L)
                .currentPage(page)
                .pageSize(pageSize)
                .hasNext(false)
                .hasPrevious(false)
                .build();


        GenericResponseDTO<PageResponseDTO<PedidoDTOResponse>> genericResponse =
                new GenericResponseDTO<>();
        genericResponse.setMessage(PEDIDO_LIST.getMessage());
        genericResponse.setObjectResponse(pageResponse);

        when(pedidoHandler.findByEstadoAndRestauranteId(estado, idRestaurante,  page, pageSize)).thenReturn(pageResponse);


        ResponseEntity<GenericResponseDTO<PageResponseDTO<PedidoDTOResponse>>> response =
                controller.listaDePedidosPorEstado(estado, idRestaurante, page, pageSize);


        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    void asignarPedido_Retorna200_CuandoRequestEsValida() {

        AuthenticatedUser user = new AuthenticatedUser(
                "10", "empleado@email.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_EMPLEADO"))
        );

        Long idPedido = 1L;
        PedidoUpdateRequest pedidoRequest = new PedidoUpdateRequest();
        PedidoUpdateDTO dto = new PedidoUpdateDTO();
        dto.setIdRestaurante(1L);
        dto.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoRequest.setRequest(dto);

        when(request.getHeader(eq("Authorization"))).thenReturn(token);
        doNothing().when(pedidoHandler).asignarPedido(idPedido, dto, token);

        ResponseEntity<GenericResponseDTO<Void>> response =
                controller.asignarPedido(request, idPedido, pedidoRequest, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void notificarPedido_Retorna200_CuandoRequestEsValida() {

        AuthenticatedUser user = new AuthenticatedUser(
                "10", "empleado@email.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_EMPLEADO"))
        );

        Long idPedido = 1L;
        PedidoUpdateRequest pedidoRequest = new PedidoUpdateRequest();
        PedidoUpdateDTO dto = new PedidoUpdateDTO();
        dto.setIdRestaurante(1L);
        dto.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoRequest.setRequest(dto);

        when(request.getHeader(eq("Authorization"))).thenReturn(token);
        doNothing().when(pedidoHandler).notificarPedido(idPedido, dto, token);

        ResponseEntity<GenericResponseDTO<Void>> response =
                controller.notificarPedido(request, idPedido, pedidoRequest, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(5)
    void entregarPedido_Retorna200_CuandoRequestEsValida() {

        AuthenticatedUser user = new AuthenticatedUser(
                "10", "empleado@email.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_EMPLEADO"))
        );

        Long idPedido = 1L;
        PedidoDeliverRequest pedidoRequest = new PedidoDeliverRequest();
        PedidoDeliverDTO dto = new PedidoDeliverDTO();
        dto.setIdRestaurante(1L);
        dto.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoRequest.setRequest(dto);

        when(request.getHeader(eq("Authorization"))).thenReturn(token);
        doNothing().when(pedidoHandler).entregarPedido(idPedido, dto, token);

        ResponseEntity<GenericResponseDTO<Void>> response =
                controller.entregarPedido(request, idPedido, pedidoRequest, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    void cancelarPedido_Retorna200_CuandoRequestEsValida() {

        AuthenticatedUser user = new AuthenticatedUser(
                "10", "empleado@email.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        Long idPedido = 1L;
        PedidoUpdateRequest pedidoRequest = new PedidoUpdateRequest();
        PedidoUpdateDTO dto = new PedidoUpdateDTO();
        dto.setIdRestaurante(1L);
        dto.setIdCliente(Long.valueOf(user.getIdUser()));
        pedidoRequest.setRequest(dto);

        when(request.getHeader(eq("Authorization"))).thenReturn(token);
        doNothing().when(pedidoHandler).cancelarPedido(idPedido, dto, token);

        ResponseEntity<GenericResponseDTO<Void>> response =
                controller.cancelarPedido(request, idPedido, pedidoRequest, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
