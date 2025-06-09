package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

public interface EndpointApi {

    String BASE_PATH_RESTAURANTE= "/api/v1/restaurante";
    String CREATE_RESTAURANTE = "/guardarRestaurante";
    String BASE_PATH_PLATO= "/api/v1/plato";
    String CREATE_PLATO = "/guardarPlato";
    String UPDATE_PLATO = "/ActualizarPlato/{idPlato}";
    String DISABLE_PLATO = "/ActualizarPlato/{idPlato}/estado";
}
