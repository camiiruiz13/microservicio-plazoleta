package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util;

public class SwaggerConstants {

    private SwaggerConstants() {
    }

    public static final String NOMBRE_DESC = "El nombre del restaurante puede contener números, pero no se permiten nombres con sólo números.";
    public static final String NOMBRE_EXAMPLE = "La fonda de beto";

    public static final String DIRECCION_DESC = "Direccion del restaurante";
    public static final String DIRECCION_EXAMPLE = "AVENIDA LA ESPAÑOLA";


    public static final String CORREO_DESC = "Correo electrónico del propietario";
    public static final String CORREO_EXAMPLE = "usuario@example.com";

    public static final String NIT_DESC = "deben ser únicamente numéricos";
    public static final String NIT_EXAMPLE = "45689236";

    public static final String TELEFONO_DESC = "El campo TelefonoRestaurante debe contener un máximo de 13 caracteres";

    public static final String TELEFONO_EXAMPLE = "+573005698325";

    public static final String IMAGE_DESC = "Campo para el logo de la imagen";
    public static final String IMAGE_EXAMPLE = "http//:www.images.com";

    public static final String STRING_TYPE= "string";


    public static final String PATTERN="^\\+?\\d{1,13}$";
}
