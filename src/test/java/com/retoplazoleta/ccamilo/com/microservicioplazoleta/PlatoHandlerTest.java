package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.PlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.ICategoriaServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IRestauranteServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
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
class PlatoHandlerTest {

    @Mock
    private ICategoriaServicePort categoriaService;

    @Mock
    private IRestauranteServicePort restauranteService;

    @Mock
    private IPlatoServicePort platoServicePort;

    @Mock
    private PlatoRequestMapper platoRequestMapper;

    @InjectMocks
    private PlatoHandler platoHandler;

    @Test
    @Order(1)
    void savePlato_deberiaGuardarCorrectamente() {

        Long idPropietario = 1L;
        Long idRestaurante = 10L;
        Long idCategoria = 5L;

        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setIdRestaurante(idRestaurante);
        platoDTO.setIdCategoria(idCategoria);

        Plato plato = new Plato();

        Restaurante restaurante = new Restaurante();
        Categoria categoria = new Categoria();

        when(platoRequestMapper.toPlato(platoDTO)).thenReturn(plato);
        when(restauranteService.findByIdAndIdPropietario(idRestaurante, idPropietario)).thenReturn(restaurante);
        when(categoriaService.findCategoriaByIdCategoria(idCategoria)).thenReturn(categoria);


        platoHandler.savePlato(platoDTO, idPropietario);

        verify(platoRequestMapper).toPlato(platoDTO);
        verify(restauranteService).findByIdAndIdPropietario(idRestaurante, idPropietario);
        verify(categoriaService).findCategoriaByIdCategoria(idCategoria);
        verify(platoServicePort).savePlato(plato);
    }

    @Test
    @Order(2)
    void updatePlato_deberiaGuardarCorrectamente() {

        Long idPlato = 1L;
        Long idPropietario = 1L;


        PlatoDTOUpdate platoDTO = new PlatoDTOUpdate();

        Plato plato = new Plato();



        when(platoRequestMapper.toPlatoUpdate(platoDTO)).thenReturn(plato);


        platoHandler.updatePlato(platoDTO, idPlato, idPropietario);

        verify(platoRequestMapper).toPlatoUpdate(platoDTO);
        verify(platoServicePort).updatePlato(plato, idPlato, idPropietario);
    }

    @Test
    @Order(3)
    void updatePlatoDisable_deberiaGuardarCorrectamente() {

        Long idPlato = 1L;
        Long idPropietario = 1L;
        Boolean enable = Boolean.FALSE;
        platoHandler.updatePlatoDisable(idPlato, enable, idPropietario);
        verify(platoServicePort).updatePlatoDisable(idPlato, enable, idPropietario);
    }
}
