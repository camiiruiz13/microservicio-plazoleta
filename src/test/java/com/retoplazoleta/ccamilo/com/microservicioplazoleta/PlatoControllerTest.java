package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller.PlatoController;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PlatoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PlatoUpdateRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PlatoControllerTest {

    @Mock
    private IPlatoHandler platoHandler;

    @InjectMocks
    private PlatoController controller;

    @Test
    @Order(1)
    void crearPlato_Retorna201_CuandoRequestEsValida() {
        // Arrange
        PlatoDTO dto = new PlatoDTO();
        PlatoRequest request = new PlatoRequest();
        request.setRequest(dto);



        AuthenticatedUser user = new AuthenticatedUser(
                "10", "carlos@mail.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_PROP"))
        );


        ResponseEntity<GenericResponseDTO<Void>> response = controller.crearPlato(request, user);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseMessage.PLATO_SUCCES.getMessage(), response.getBody().getMessage());
        verify(platoHandler).savePlato(dto, Long.valueOf(user.getIdUser()));
    }

    @Test
    @Order(2)
    void actualizarPlato_Retorna200_CuandoRequestEsValida() {

        Long idPlato = 1L;
        PlatoDTOUpdate dto = new PlatoDTOUpdate();
        PlatoUpdateRequest request = new PlatoUpdateRequest();
        request.setRequest(dto);

        AuthenticatedUser user = new AuthenticatedUser(
                "20", "propietario@mail.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_PROP"))
        );


        ResponseEntity<GenericResponseDTO<Void>> response = controller.actuaalizarPlato(idPlato, request, user);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseMessage.PLATO_UPDATE_SUCCES.getMessage(), response.getBody().getMessage());
        verify(platoHandler).updatePlato(dto, idPlato, Long.valueOf(user.getIdUser()));
    }

    @Test
    @Order(3)
    void activarDesactivarPlato_Retorna200_CuandoRequestEsValida() {

        Long idPlato = 5L;
        boolean activo = true;

        AuthenticatedUser user = new AuthenticatedUser(
                "99", "duenio@mail.com", null,
                List.of(new SimpleGrantedAuthority("ROLE_PROP"))
        );

        String expectedMessage = ResponseMessage.PLATO.getMessage()
                + ResponseMessage.HABILITADO.getMessage()
                + ResponseMessage.SUCCES_DISABLE.getMessage();


        ResponseEntity<GenericResponseDTO<Void>> response = controller.activarDesactivarPlato(idPlato, activo, user);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody().getMessage());
        verify(platoHandler).updatePlatoDisable(idPlato, activo, Long.valueOf(user.getIdUser()));
    }


}
