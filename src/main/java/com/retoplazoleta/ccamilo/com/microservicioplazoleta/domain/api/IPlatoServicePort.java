package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.api;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Plato;

public interface IPlatoServicePort {

     void savePlato(Plato plato);
     void updatePlato(Plato plato, Long id);
     Plato findById(Long id);
}
