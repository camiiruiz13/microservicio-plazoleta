package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

public interface EndpointApi {

    String BASE_PATH_RESTAURANTE= "/api/v1/restaurante";
    String CREATE_RESTAURANTE = "/guardarRestaurante";
    String FIND_ALL_RESTAURANTE = "/restaurantes";
    String FIND_ALL_PLATOS = "/platos/{idRestaurante}/restaurante";
    String BASE_PATH_PLATO= "/api/v1/plato";
    String BASE_PATH_PEDIDO= "/api/v1/pedidos";
    String CREATE_PLATO = "/guardarPlato";
    String UPDATE_PLATO = "/ActualizarPlato/{idPlato}";
    String DISABLE_PLATO = "/ActualizarPlato/{idPlato}/estado";
    String CREATE_PEDIDOS = "/crearPedidos";
    String ASIGNAR_PEDIDO = "/asignarPedido/{id}";
    String NOTIFICAR_PEDIDO = "/notificarPedido/{id}";
    String ENTREGAR_PEDIDO = "/entregarPedido/{id}";
    String CANCELAR_PEDIDO = "/cancelarPedido/{id}";
    String LIST_PEDIDOS_BY_ESTADO = "/obtenerPedido/{estado}/restaurante/{idRestaurante}";
    String FILTRAR_PEDIDOS_ID_RESTAURANTE = "/{idRestaurante}";
}
