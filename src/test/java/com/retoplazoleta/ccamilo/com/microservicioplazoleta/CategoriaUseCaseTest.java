package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.CategoriaValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.ICategoriaPersistencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.usecase.CategoriaUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CategoriaUseCaseTest {

    @Mock
    private ICategoriaPersistencePort categoriaPersistencePort;

    @InjectMocks
    private CategoriaUseCase categoriaUseCase;

    @Test
    @Order(1)
    void cuandoIdCategoriaEsNull_lanzaExcepcion() {


        CategoriaValidationException ex = assertThrows(
                CategoriaValidationException.class,
                () -> categoriaUseCase.findCategoriaByIdCategoria(null)
        );

        assertEquals(ID_CATEGORIA_NULL.getMessage(), ex.getMessage());
    }

    @Test
    @Order(2)
    void cuandoCategoriaNoExiste_lanzaExcepcion() {
        Long idCategoria = 10L;

        when(categoriaPersistencePort.findByIdCategoria(idCategoria)).thenReturn(null);

        CategoriaValidationException ex = assertThrows(
                CategoriaValidationException.class,
                () -> categoriaUseCase.findCategoriaByIdCategoria(idCategoria)
        );

        assertEquals(ERROR_CATEGORIA.getMessage(), ex.getMessage());
    }

    @Test
    @Order(3)
    void cuandoExisteCategoria_retornaCategoria() {
        Long idCategoria = 1L;

        Categoria categoria = builCategoria();

        when(categoriaPersistencePort.findByIdCategoria(idCategoria)).thenReturn(categoria);

        Categoria resultado = categoriaPersistencePort.findByIdCategoria(idCategoria);

        assertNotNull(resultado);
        assertEquals(categoria, resultado);
    }

     private Categoria builCategoria(){
         Categoria categoria = new Categoria();
         categoria.setId(1L);
         categoria.setNombre("Bebidas");
         categoria.setDescripcion("Bebidas frías y calientes");
         return categoria;
     }
}
