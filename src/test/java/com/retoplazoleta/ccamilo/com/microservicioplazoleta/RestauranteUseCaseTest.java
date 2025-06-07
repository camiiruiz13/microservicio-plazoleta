package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.RestauranteUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    @Mock
    private IRestaurantePersitencePort restaurantePersitencePort;

    @Mock
    private IApiClientPort apiClientPort;

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;

    @Test
    @Order(1)
    void testSaveRestaurante_Valid() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Mi Restaurante");
        restaurante.setNit("123456");
        restaurante.setTelefono("+573001112233");

        restauranteUseCase.saveRestaurante(restaurante);

        verify(restaurantePersitencePort).saveRestaurante(restaurante);
    }


    @Test
    @Order(2)
    void testNombreNull_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(null);

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El nombre es obligatorio.", ex.getMessage());
    }

    @Test
    @Order(3)
    void testNombreVacio_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("   ");

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El nombre es obligatorio.", ex.getMessage());
    }

    @Test
    @Order(4)
    void testNombreSoloNumeros_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("123456");

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El nombre no puede estar compuesto solo por números.", ex.getMessage());
    }

    @Test
    @Order(5)
    void testNitNull_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Valido");
        restaurante.setNit(null);

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El NIT debe contener solo números.", ex.getMessage());
    }

    @Test
    @Order(6)
    void testNitNoNumerico_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Valido");
        restaurante.setNit("ABC123");

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El NIT debe contener solo números.", ex.getMessage());
    }

    @Test
    @Order(7)
    void testTelefonoNull_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Valido");
        restaurante.setNit("123456");
        restaurante.setTelefono(null);

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El teléfono debe tener máximo 13 caracteres y ser numérico. Puede iniciar con +.", ex.getMessage());
    }

    @Test
    @Order(8)
    void testTelefonoInvalido_LanzaExcepcion() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Valido");
        restaurante.setNit("123456");
        restaurante.setTelefono("12345678901234"); // 14 caracteres, inválido

        RestauranteValidationException ex = assertThrows(RestauranteValidationException.class, () ->
                restauranteUseCase.saveRestaurante(restaurante));
        assertEquals("El teléfono debe tener máximo 13 caracteres y ser numérico. Puede iniciar con +.", ex.getMessage());
    }

    @Test
    @Order(9)
    void testIdPropietario_Delegacion() {
        String correo = "usuario@mail.com";
        String token = "Bearer abc123";

        when(apiClientPort.idPropietario(correo, token)).thenReturn(10L);

        Long result = restauranteUseCase.idPropietario(correo, token);

        assertEquals(10L, result);
        verify(apiClientPort).idPropietario(correo, token);
    }

}
