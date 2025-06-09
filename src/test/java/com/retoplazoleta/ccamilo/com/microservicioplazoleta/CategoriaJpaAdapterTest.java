package com.retoplazoleta.ccamilo.com.microservicioplazoleta;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter.CategoriaJpaAdapter;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.CategoriaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CategoriaJpaAdapterTest {

    @Mock
    private ICategoriaEntityMapper categoriaEntityMapper;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaJpaAdapter categoriaJpaAdapter;

    @Test
    @Order(1)
    void getCategoriaBy_debeRetornarCategoriaCuandoExiste() {

        Long id = 1L;
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        Categoria categoria = new Categoria();

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaEntity));
        when(categoriaEntityMapper.toCategoriaModel(categoriaEntity)).thenReturn(categoria);

        Categoria resultado = categoriaJpaAdapter.findByIdCategoria(id);

        assertNotNull(resultado);
        verify(categoriaRepository).findById(id);
        verify(categoriaEntityMapper).toCategoriaModel(categoriaEntity);


    }

    @Test
    @Order(2)
    void getUsuarioByCorreo_debeLanzarExcepcionCuandoNoExiste() {
        Long id = 1000L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        Categoria resultado = categoriaJpaAdapter.findByIdCategoria(id);

        assertNull(resultado);
        verify(categoriaRepository).findById(id);
        verifyNoInteractions(categoriaEntityMapper);
    }



}
