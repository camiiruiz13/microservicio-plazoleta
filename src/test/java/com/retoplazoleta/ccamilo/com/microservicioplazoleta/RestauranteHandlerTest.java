package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.RestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.RestauranteRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class RestauranteHandlerTest {

    @Mock
    private RestauranteRequestMapper restauranteRequestMapper;

    @Mock
    private IRestauranteServicePort restauranteServicePort;

    @InjectMocks
    private RestauranteHandler restauranteHandler;

    @Test
    @Order(1)
    void testSaveRestaurante() {

        RestauranteDTO dto = new RestauranteDTO();
        dto.setNombre("Mi Restaurante");
        dto.setNit("123456");
        dto.setTelefono("+573001112233");
        dto.setCorreo("usuario@mail.com");

        Restaurante restauranteMapped = new Restaurante();
        when(restauranteRequestMapper.toRestaurante(dto)).thenReturn(restauranteMapped);
        when(restauranteServicePort.idPropietario(dto.getCorreo(), "Bearer token"))
                .thenReturn(1L);


        restauranteHandler.saveRestaurante(dto, "Bearer token");

        verify(restauranteRequestMapper).toRestaurante(dto);
        verify(restauranteServicePort).idPropietario(dto.getCorreo(), "Bearer token");
        verify(restauranteServicePort).saveRestaurante(restauranteMapped);
    }
}
