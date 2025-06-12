package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.util;

public class SwaggerConstants {

    private SwaggerConstants() {
    }

    public static final String RESTAURANTE_NOMBRE_DESC = "El nombre del restaurante puede contener números, pero no se permiten nombres con sólo números.";
    public static final String RESTAURANTE_NOMBRE_EXAMPLE = "La fonda de beto";

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

    public static final String PLATO_NOMBRE_DESC = "Nombre del plato";
    public static final String PLATO_NOMBRE_EXAMPLE = "Bandeja paisa";

    public static final String PLATO_DESC_DESC = "Descripcion del plato";
    public static final String PLATO_DESC_EXAMPLE = "Plato compuesto de frijol, chicharron y arroz";


    public static final String PLATO_PRECIO_DESC = "Precio del plato en números enteros positivos";
    public static final String PLATO_PRECIO_EXAMPLE = "25.630";

    public static final String PLATO_ID_RESTAURANTE_DESC = "Id del restaurante";
    public static final String PLATO_ID_RESTAURANTE_EXAMPLE = "1";

    public static final String PLATO_ID_DESC = "Id del plato";
    public static final String PLATO_ID_EXAMPLE = "1";

    public static final String CANTIDAD_DESC = "Cantidad de platos";
    public static final String CANTIDAD_EXAMPLE = "10";

    public static final String PLATOS =
            "Lista de platos con su cantidad.";

    public static final String PLATO_ID_CATEGORIA_DESC = "Id de la categoria";
    public static final String PLATO_ID_CATEGORIA_EXAMPLE = "1";

    public static final String STRING_TYPE= "string";
    public static final String NUMBER_TYPE= "number";
    public static final String FORMAT_DOUBLE = "double";
    public static final String FORMAT_LONG = "int64";
    public static final String FORMAT_INTEGER = "int32";


    public static final String PATTERN="^\\+?\\d{1,13}$";
}
