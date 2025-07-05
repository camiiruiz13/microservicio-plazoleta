package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationConstant {

    REQUIRED_NAME("El nombre es obligatorio."),
    NUMERIC_NAME("El nombre no puede estar compuesto solo por números."),
    NUMERIC_NIT("El NIT debe contener solo números."),
    CARACTER_TEL("El teléfono debe tener máximo 13 caracteres y ser numérico. Puede iniciar con +."),
    USER_PROPIETARIO("Usuario  propietario del restaurante"),
    ID_RESTAURANTE_NULL("El id del restaurante no puede ser nulo"),
    ID_CATEGORIA_NULL("El id del categoria no puede ser nulo"),
    ID_PLATO_NULL("No exite el plato a modifificar , registre uno"),
    ID_PEDIDO_NULL("No exite el plato a modifificar , registre uno. Pedido ID: "),
    ID_PLATO_PEDIDO_NULL("No exite el plato para crear pedido , registre uno"),
    ERROR_USER("El usuario no es propietario del restaurante"),
    ERROR_CATEGORIA("La categoria no existe"),
    NAME_PLATO_EXCEPTION("El nombre del plato es obligatorio"),
    DESCRIPTION_PLATO_EXCEPTION("La descripción del plato es obligatoria"),
    URL_PLATO_EXCEPTION("La URL de la imagen es obligatoria"),
    PRICE_PLATO_EXCEPTION("El precio del plato debe ser mayor a 0"),
    PRICE_PLATO_NULL_EXCEPTION("El precio no debe ser nulo"),
    PEDIDO_NO_EXITS("El pedido no contiene platos."),
    PEDIDO_PROCESS("El cliente ya tiene un pedido en proceso."),
    PEDIDO_PROCESS_CANCELED("El cliente no puede cancelar pedido , porque no tiene pedido en proceso"),
    PEDIDO_CANCELED("El cliente no pertenece al restaurante."),
    PEDIDO_PLATO_RESTAURANTE("El plato no pertence al restaurante"),
    PEDIDO_PLATO_EMPLEADO_RESTAURANTE("El empleado no pertenece al restaurante ID EMPLEADO "),
    CODIGO_PEDIDO("El codigo del pedido es incorrecto "),
    EMPLEADO_PLATO_RESTAURANTE("No se puede cambiar el estado, asignese el pedido ID EMPLEADO "),
    PEDIDO_RESTAURANTE("El pedido no pertenece al restaurante. Restaurante ID: "),
    USER_NOT_RESTAURANT("El empleado no pertenece al restaurante. Empleado ID: "),
    MATCHES("^\\d+$"),
    PEDIDO_ESTADO_PREPARACION("El pedido ya tiene asignado un estado "),
    PEDIDO_ESTADO_DIFERENTE("El pedido ya tiene asignado un estado diferente a " ),
    PEDIDO_ESTADO_DIFERENTE_PENDIENTE("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse" ),
    NO_EXISTE_PEDIDO_RESTAURANTE ("No existen pedidos en el restaurante "),
    MATCHES_TEL("^\\+?\\d{1,13}$");

    private final String message;
}
