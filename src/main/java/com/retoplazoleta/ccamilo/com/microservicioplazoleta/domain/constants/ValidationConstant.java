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
    USER_PROPIETARIO("Usuario no propietario del restaurante"),
    ERROR_USER("El usuario no es propietario del restaurante"),
    MATCHES("^\\d+$"),
    MATCHES_TEL("^\\+?\\d{1,13}$");

    private final String message;
}
