package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller.PlatoController;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PlatoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage;
import org.junit.jupiter.api.Test;
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

@ExtendWith(MockitoExtension.class)
class PlatoControllerTest {

    @Mock
    private IPlatoHandler platoHandler;

    @InjectMocks
    private PlatoController controller;

    @Test
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
}
