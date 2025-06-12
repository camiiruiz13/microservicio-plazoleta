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
    ERROR_USER("El usuario no es propietario del restaurante"),
    ERROR_CATEGORIA("La categoria no existe"),
    NAME_PLATO_EXCEPTION("El nombre del plato es obligatorio"),
    DESCRIPTION_PLATO_EXCEPTION("La descripción del plato es obligatoria"),
    URL_PLATO_EXCEPTION("La URL de la imagen es obligatoria"),
    PRICE_PLATO_EXCEPTION("El precio del plato debe ser mayor a 0"),
    PRICE_PLATO_NULL_EXCEPTION("El precio no debe ser nulo"),
    PEDIDO_NO_EXITS("El pedido no contiene platos."),
    PEDIDO_PROCESS("El cliente ya tiene un pedido en proceso."),
    PEDIDO_PLATO_RESTAURANTE("El plato no pertence al restaurante"),
    MATCHES("^\\d+$"),
    MATCHES_TEL("^\\+?\\d{1,13}$");

    private final String message;
}
