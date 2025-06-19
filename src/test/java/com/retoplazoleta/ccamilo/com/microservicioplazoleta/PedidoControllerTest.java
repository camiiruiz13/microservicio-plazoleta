package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller.Pedidoontroller;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private IPedidoHandler pedidoHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private Pedidoontroller controller;

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
        assertEquals(ResponseMessage.PEDIDO_SUCCES.getMessage(), response.getBody().getMessage());
        verify(pedidoHandler).savePedido(dto);
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

        when(pedidoHandler.findByEstadoAndRestauranteId(estado , idRestaurante, Long.valueOf(user.getIdUser()), page, pageSize)).thenReturn(pageResponse);


        ResponseEntity<GenericResponseDTO<PageResponseDTO<PedidoDTOResponse>>> response =
                controller.listaDePedidosPorEstado(estado, idRestaurante,  page, pageSize, user);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PEDIDO_LIST.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getObjectResponse().getContent().size());
        verify(pedidoHandler).findByEstadoAndRestauranteId(estado , idRestaurante, Long.valueOf(user.getIdUser()), page, pageSize);
    }

    @Test
    @Order(3)
    void actualizarPedido_Retorna200_CuandoRequestEsValida() {

        Long idPedido = 10L;
        String token = "Bearer token";

        AuthenticatedUser user = new AuthenticatedUser(
                "10", "empleado@email.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_EMPLEADO"))
        );


        PedidoUpdateDTO dto = new PedidoUpdateDTO();
        dto.setIdChef(Long.valueOf(user.getIdUser()));
        PedidoUpdateRequest request = new PedidoUpdateRequest();
        request.setRequest(dto);

        ResponseEntity<GenericResponseDTO<Void>> response = controller.actualizarPedido(httpServletRequest, idPedido, request, user);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PEDIDO_UPDATE_SUCCES.getMessage(), response.getBody().getMessage());
        verify(pedidoHandler).updatePedido(idPedido,dto, token);
    }



}
