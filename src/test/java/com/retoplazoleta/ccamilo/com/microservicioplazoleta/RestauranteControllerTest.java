package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IRestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller.RestauranteController;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.RestauranteRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.RESTAURANT_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    @Mock
    private IRestauranteHandler restauranteHandler;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RestauranteController controller;

    @Test
    void testCreateRestaurante_Returns201() {

        RestauranteRequest restauranteRequest = new RestauranteRequest();
        RestauranteDTO dto = new RestauranteDTO();
        dto.setNombre("La fonda de Beto");
        dto.setDireccion("Calle 123 #45-67");
        dto.setCorreo("usuario@example.com");
        dto.setNit("123456789");
        dto.setTelefono("+573001112233");
        dto.setUrlLogo("http://images.com/logo.png");
        restauranteRequest.setRequest(dto);

        String token = "Bearer abc.def";


        when(request.getHeader(eq("Authorization"))).thenReturn(token);
        doNothing().when(restauranteHandler).saveRestaurante(dto, token);


        ResponseEntity<GenericResponseDTO<Void>> response =
                controller.crearRestaurante(request, restauranteRequest);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseMessage.RESTAURANT_SUCCES.getMessage(), response.getBody().getMessage());
        verify(restauranteHandler).saveRestaurante(dto, token);
        verify(request).getHeader("Authorization");
    }

    @Test
    void testListarRestaurantes_Returns200() {
        int page = 0;
        int pageSize = 5;



        PageResponseDTO<RestauranteDTOPage> pageResponse = PageResponseDTO.<RestauranteDTOPage>builder()
                .content(List.of(new RestauranteDTOPage()))
                .totalPages(1)
                .totalElements(1L)
                .currentPage(page)
                .pageSize(pageSize)
                .hasNext(false)
                .hasPrevious(false)
                .build();


        GenericResponseDTO<PageResponseDTO<RestauranteDTOPage>> genericResponse =
                new GenericResponseDTO<>();
        genericResponse.setMessage(RESTAURANT_LIST.getMessage());
        genericResponse.setObjectResponse(pageResponse);

        when(restauranteHandler.findAllRestaurantes(page, pageSize)).thenReturn(pageResponse);


        ResponseEntity<GenericResponseDTO<PageResponseDTO<RestauranteDTOPage>>> response =
                controller.listarRestaurantes(page, pageSize);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESTAURANT_LIST.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getObjectResponse().getContent().size());
        verify(restauranteHandler).findAllRestaurantes(page, pageSize);
    }

}
