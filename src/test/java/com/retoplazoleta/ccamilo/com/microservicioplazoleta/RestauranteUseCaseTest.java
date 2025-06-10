package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
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

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.ERROR_USER;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.ID_RESTAURANTE_NULL;
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
        Restaurante restaurante = new Restaurante();

        when(apiClientPort.idPropietario(correo, token, restaurante)).thenReturn(10L);

        Long result = restauranteUseCase.idPropietario(correo, token, restaurante);

        assertEquals(10L, result);
        verify(apiClientPort).idPropietario(correo, token, restaurante);
    }


    @Test
    @Order(10)
    void cuandoIdRestauranteEsNull_lanzaExcepcion() {
        Long idPropietario = 1L;

        RestauranteValidationException ex = assertThrows(
                RestauranteValidationException.class,
                () -> restauranteUseCase.findByIdAndIdPropietario(null, idPropietario)
        );

        assertEquals(ID_RESTAURANTE_NULL.getMessage(), ex.getMessage());
    }

    @Test
    @Order(11)
    void cuandoRestauranteNoExiste_lanzaExcepcion() {
        Long idRestaurante = 10L;
        Long idPropietario = 1L;

        when(restaurantePersitencePort.findByIdAndIdPropietario(idRestaurante, idPropietario)).thenReturn(null);

        RestauranteValidationException ex = assertThrows(
                RestauranteValidationException.class,
                () -> restauranteUseCase.findByIdAndIdPropietario(idRestaurante, idPropietario)
        );

        assertEquals(ERROR_USER.getMessage(), ex.getMessage());
    }

    @Test
    @Order(12)
    void cuandoExisteRestaurante_retornaRestaurante() {
        Long idRestaurante = 1L;
        Long idPropietario = 1L;

        Restaurante restaurante = builRestaurante();

        when(restaurantePersitencePort.findByIdAndIdPropietario(idRestaurante, idPropietario)).thenReturn(restaurante);

        Restaurante resultado = restauranteUseCase.findByIdAndIdPropietario(idRestaurante, idPropietario);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
    }

    @Test
    @Order(13)
    void listarRestaurantesPaginados_debeRetornarListaPaginada() {

        int page = 0;
        int size = 2;

        List<Restaurante> restaurantes = List.of(
                builRestaurante(),
                builRestaurante()
        );

        PageResponse<Restaurante> pageResult = PageResponse.<Restaurante>builder()
                .content(restaurantes)
                .currentPage(page)
                .pageSize(size)
                .totalElements(2)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(restaurantePersitencePort.findAllRestaurantes(page, size)).thenReturn(pageResult);

        PageResponse<Restaurante> resultado = restauranteUseCase.findAllRestaurantes(page, size);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(restaurantePersitencePort).findAllRestaurantes(page, size);
    }


    private Restaurante builRestaurante(){
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Prueba test");
        restaurante.setDireccion("Direccion Test");
        restaurante.setIdPropietario(1L);
        restaurante.setNit("2563698");
        restaurante.setTelefono("+573005698325");
        restaurante.setUrlLogo("http://www.images.com");
        return restaurante;

    }

}
