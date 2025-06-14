package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Categoria;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

public interface IPlatoServicePort {

     void savePlato(Plato plato);
     Categoria findCategoriaByIdCategoria(Long id);
     Restaurante findByIdAndIdPropietario(Long idRestaurante, Long idPropietario);
     void updatePlato(Plato plato, Long id, Long idPropietario);
     void updatePlatoDisable(Long id, Boolean activo, Long idPropietario);
     Plato findByIdAndIdRPropietario(Long id, Long idProietario);
     PageResponse<Plato> findByPlatoByRestaurantes(Long idRestaurante, Long idCategoria, int page, int pageSize);
}
