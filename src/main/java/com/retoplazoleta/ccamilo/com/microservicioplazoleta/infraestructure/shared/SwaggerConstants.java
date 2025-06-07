package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

public class SwaggerConstants {

    private SwaggerConstants() {
    }

    public static final String TAG_RESTAURANTE = "Restaurante";
    public static final String TAG_RESTAURANTE_DESC = """
             Crear en el sistema los restaurantes
            para brindarle al cliente la posibilidad de escoger en cual pedir sus alimentos
            """;
    public static final String OP_CREAR_RESTAURANTE_SUMMARY = "Crear Restaurante";
    public static final String OP_CREAR_RESTAURANTE_DESC = "Crear un nuevo restaurante en la base de datos con rol usuario administrador.";
    public static final String CREATE_RESTAURANTE_DESCRIPTION_REQUEST = "Crear Restaurante";

    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String NOT_FOUND = "404";
    public static final String INTERNAL_SERVER_ERROR = "500";

    public static final String RESPONSE_200_DESC = "Operación realizada correctamente.";
    public static final String RESPONSE_201_DESC = "Recurso creado correctamente.";
    public static final String RESPONSE_400_DESC = "Solicitud inválida o datos incorrectos.";
    public static final String RESPONSE_401_DESC = "Token inválido o no enviado.";
    public static final String RESPONSE_403_DESC = "Acceso denegado: no tiene permisos suficientes.";
    public static final String RESPONSE_404_DESC = "Recurso no encontrado.";
    public static final String RESPONSE_500_DESC = "Error interno del servidor.";
}
