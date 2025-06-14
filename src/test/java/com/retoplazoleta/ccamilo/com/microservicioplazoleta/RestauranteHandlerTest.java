package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.RestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.IRestauranteResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.RestauranteRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class RestauranteHandlerTest {

    @Mock
    private RestauranteRequestMapper restauranteRequestMapper;

    @Mock
    private IRestauranteResponseMapper restauranteResponseMapper;

    @Mock
    private IRestauranteServicePort restauranteServicePort;

    @InjectMocks
    private RestauranteHandler restauranteHandler;

    @Test
    @Order(1)
    void testSaveRestaurante() {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setCorreo("correo@test.com");

        Restaurante restaurante = new Restaurante();
        when(restauranteRequestMapper.toRestaurante(dto)).thenReturn(restaurante);

        String token = "Bearer token";
        doNothing().when(restauranteServicePort).saveRestaurante(restaurante, dto.getCorreo(), token);

        restauranteHandler.saveRestaurante(dto, token);

        verify(restauranteRequestMapper).toRestaurante(dto);
        verify(restauranteServicePort).saveRestaurante(restaurante, dto.getCorreo(), token);
    }

    @Test
    @Order(2)
    void testFindAllRestaurantes() {
        int page = 0;
        int pageSize = 5;


        Restaurante restaurante = new Restaurante();
        RestauranteDTOPage restauranteDTOPage = new RestauranteDTOPage();


        PageResponse<Restaurante> pageResponse = PageResponse.<Restaurante>builder()
                .content(List.of(restaurante))
                .totalPages(1)
                .totalElements(1L)
                .currentPage(page)
                .pageSize(pageSize)
                .hasNext(false)
                .hasPrevious(false)
                .build();


        when(restauranteServicePort.findAllRestaurantes(page, pageSize)).thenReturn(pageResponse);
        when(restauranteResponseMapper.toResponseRestauranteList(restaurante)).thenReturn(restauranteDTOPage);


        PageResponseDTO<RestauranteDTOPage> result = restauranteHandler.findAllRestaurantes(page, pageSize);


        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertEquals(page, result.getCurrentPage());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(1, result.getContent().size());
        assertEquals(restauranteDTOPage, result.getContent().get(0)); // También verifica el contenido esperado


        verify(restauranteServicePort).findAllRestaurantes(page, pageSize);
        verify(restauranteResponseMapper).toResponseRestauranteList(restaurante);
    }
}
