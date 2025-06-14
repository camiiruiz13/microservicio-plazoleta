package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PlatoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.impl.PlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoRequestMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper.PlatoResponseMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api.IPlatoServicePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
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
class PlatoHandlerTest {



    @Mock
    private IPlatoServicePort platoServicePort;

    @Mock
    private PlatoRequestMapper platoRequestMapper;

    @Mock
    private PlatoResponseMapper platoResponseMapper;

    @InjectMocks
    private PlatoHandler platoHandler;

    @Test
    @Order(1)
    void savePlato_deberiaGuardarCorrectamente() {


        Long idRestaurante = 10L;
        Long idCategoria = 5L;

        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setIdRestaurante(idRestaurante);
        platoDTO.setIdCategoria(idCategoria);

        Plato plato = new Plato();



        when(platoRequestMapper.toPlato(platoDTO)).thenReturn(plato);


        platoHandler.savePlato(platoDTO);

        verify(platoRequestMapper).toPlato(platoDTO);
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

    @Test
    @Order(4)
    void testFindAllPlatosByRestaurantes() {
        int page = 0;
        int size = 2;
        Long idRestaurante = 1L;
        Long idCategoria = 1L;


        Plato plato = new Plato();
        PlatoDTOResponse platoDTOPage = new PlatoDTOResponse();


        PageResponse<Plato> pageResponse = PageResponse.<Plato>builder()
                .content(List.of(plato))
                .totalPages(1)
                .totalElements(1L)
                .currentPage(page)
                .pageSize(size)
                .hasNext(false)
                .hasPrevious(false)
                .build();


        when(platoServicePort.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, size)).thenReturn(pageResponse);
        when(platoResponseMapper.toPlatoDTo(plato)).thenReturn(platoDTOPage);


        PageResponseDTO<PlatoDTOResponse> result = platoHandler.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, size);


        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertEquals(page, result.getCurrentPage());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getContent().size());
        assertEquals(platoDTOPage, result.getContent().get(0));


        verify(platoServicePort).findByPlatoByRestaurantes(idRestaurante, idCategoria, page, size);
        verify(platoResponseMapper).toPlatoDTo(plato);
    }
}
