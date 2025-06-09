package com.retoplazoleta.ccamilo.com.microservicioplazoleta;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IPlatoPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.PlatoUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PlatoUseCaseTest {

    @Mock
    private IPlatoPersistencePort platoPersistencePort;



    @InjectMocks
    private PlatoUseCase platoUseCase;

    @Test
    @Order(1)
    void testSavePlato_Valid() {

        Plato plato = builPlato ();

        platoUseCase.savePlato(plato);

        verify(platoPersistencePort).savePlato(plato);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @Order(2)
    void testPlatoNombreVacioLanzaExcepion(String nombreInvalido) {
        Plato plato = builPlato();
        plato.setNombre(nombreInvalido);

        PlatoValidationException ex = assertThrows(PlatoValidationException.class, () ->
                platoUseCase.savePlato(plato));
        assertEquals(NAME_PLATO_EXCEPTION.getMessage(), ex.getMessage());
    }




    @Test
    @Order(3)
    void testPlatoMenorA0LanzaExcepcion() {
        Plato plato = builPlato();
        plato.setPrecio(-155.3);

        PlatoValidationException ex = assertThrows(PlatoValidationException.class, () ->
                platoUseCase.savePlato(plato));
        assertEquals(PRICE_PLATO_EXCEPTION.getMessage(), ex.getMessage());
    }


    @Test
    @Order(4)
    void testPlatoPrecioNullLanzaExcepion() {
        Plato plato = builPlato();
        plato.setPrecio(null);

        PlatoValidationException ex = assertThrows(PlatoValidationException.class, () ->
                platoUseCase.savePlato(plato));
        assertEquals(PRICE_PLATO_NULL_EXCEPTION.getMessage(), ex.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @Order(5)
    void testPlatoDescripcionVacioLanzaExcepion(String descripcionInvalida) {
        Plato plato = builPlato();
        plato.setDescripcion(descripcionInvalida);

        PlatoValidationException ex = assertThrows(PlatoValidationException.class, () ->
                platoUseCase.savePlato(plato));
        assertEquals(DESCRIPTION_PLATO_EXCEPTION.getMessage(), ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @Order(6)
    void testPlatoUrlacioLanzaExcepion(String url) {
        Plato plato = builPlato();
        plato.setUrlImagen(url);

        PlatoValidationException ex = assertThrows(PlatoValidationException.class, () ->
                platoUseCase.savePlato(plato));
        assertEquals(URL_PLATO_EXCEPTION.getMessage(), ex.getMessage());
    }

    @Test
    @Order(7)
    void cuandoIdPlatoEsNull_lanzaExcepcion() {


        PlatoValidationException ex = assertThrows(
                PlatoValidationException.class,
                () -> platoUseCase.findById(null)
        );

        assertEquals(ID_PLATO_NULL.getMessage(), ex.getMessage());
    }



    @Test
    @Order(8)
    void cuandoExistePlato_retornaPlato() {
        Long idPlato = 1L;

        Plato plato = builPlato();

        when(platoPersistencePort.findById(idPlato)).thenReturn(plato);

        Plato resultado = platoUseCase.findById(idPlato);

        assertNotNull(resultado);
        assertEquals(plato, resultado);
    }


    Plato builPlato (){

        Plato plato = new Plato();
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Prueba test");
        restaurante.setDireccion("Direccion Test");
        restaurante.setIdPropietario(1L);
        restaurante.setNit("2563698");
        restaurante.setTelefono("+573005698325");
        restaurante.setUrlLogo("http://www.images.com");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebidas");
        categoria.setDescripcion("Bebidas frías y calientes");

        plato.setId(1L);
        plato.setNombre("Jugo de Mango");
        plato.setCategoria(categoria);
        plato.setDescripcion("Jugo natural de mango sin azúcar");
        plato.setPrecio(8000.0);
        plato.setRestaurante(restaurante);
        plato.setUrlImagen("http://www.images.com/jugo-mango.jpg");
        plato.setActivo(true);

        return plato;
    }

}
