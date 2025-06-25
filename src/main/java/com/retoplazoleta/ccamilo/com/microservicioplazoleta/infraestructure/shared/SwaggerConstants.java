package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared;

public class SwaggerConstants {

    private SwaggerConstants() {
    }

    public static final String EXAMPLES_ID = "1";

    public static final String TAG_RESTAURANTE = "Restaurante";
    public static final String TAG_RESTAURANTE_DESC = """
             Crear en el sistema los restaurantes
            para brindarle al cliente la posibilidad de escoger en cual pedir sus alimentos
            """;
    public static final String OP_CREAR_RESTAURANTE_SUMMARY = "Crear Restaurante";
    public static final String OP_CREAR_RESTAURANTE_DESC = "Crear un nuevo restaurante en la base de datos con rol usuario administrador.";
    public static final String CREATE_RESTAURANTE_DESCRIPTION_REQUEST = "Objeto de Crear Restaurante";

    public static final String OP_LISTAR_RESTAURANTE_SUMMARY = "Listar restaurantes";
    public static final String OP_LISTAR_RESTAURANTE_DESC = "Listar los restaurantes disponibles\n" +
            "para poder elegir en cual deseo ordenar un plato";


    public static final String TAG_PLATO = "Plato";
    public static final String TAG_PLATO_DESC = """
            El  propietario de un restaurante
             necesito crear platos para asociarlos al menú de mi restaurante
             para brindarle diferentes opciones de platos al cliente
            """;

    public static final String OP_CREAR_PLATO_SUMMARY = "Crear plato";
    public static final String OP_CREAR_PLATO_DESC = "Crear un nuevo plato en la base de datos con el usuario propietario registrado en el restaurante";
    public static final String CREATE_PLATO_DESCRIPTION_REQUEST = "Objeto de crear plato";

    public static final String OP_UPDATE_PLATO_SUMMARY = "Actualizar plato";
    public static final String OP_UPDATE_PLATO_DESC = """
            Actualizar la información de los platos en el menú
            para corregir valores errados o actualizar precios
            """;

    public static final String OP_DISABLE_PLATO_SUMMARY = "Activar inactivar plato";
    public static final String OP_DISABLE_PLATO_DESC = """
            Activar/desactivar platos en el menú\s
            para dejar de ofrecer el producto en el menú
            """;

    public static final String OP_DISABLE_DESCRIPTION = "Estado del plato (true = activo, false = inactivo)";
    public static final String OP_ESTADODISABLE_DESCRIPTION = "Estados del pedido solicitado";
    public static final String OP_DISABLE_EXAMPLE = "false";
    public static final String OP_DISABLE_EXAMPLE_TRUE = "true";
    public static final String BOOLEAN_TYPE= "boolean";
    public static final String STRING_TYPE= "string";

    public static final String DESC_PENDIENTE ="PENDIENTE";
    public static final String DESC_EN_PREPARACION = "EN_PREPARACION";
    public static final String DESC_ENTREGADO= "ENTREGADO";
    public static final String DESC_CANCELADO = "CANCELADO";
    public static final String DESC_LISTO = "CANCELADO";


    public static final String UPDATE_PLATO_DESCRIPTION_REQUEST = "Objeto de crear plato";

    public static final String OP_LISTAR_PLATO_SUMMARY = "Listar plato";
    public static final String OP_LISTAR_PLATO_DESC = """
            Listar el menú de cada restaurante al que le doy clic
            para poder solicitar el plato de mi preferencia
            """;
    public static final String OP_FILTER_ID_CATEGORIA = "ID de la categoría para filtrar los platos. Si se omite, se listan todas las categorías.";
    public static final String OP_FILTER_ID_RESTAURANTE = "ID del restaurante para listar su menú";
    public static final String OP_FILTER_ID_PEDIDO = "ID del pedido para actualizar";

    public static final String TAG_PEDIDO = "Plato";
    public static final String TAG_PEDIDO_DESC = """
            Solicitar los platos de mi preferencia
            para que puedan prepararlos y traerlos a mi mesa
            """;

    public static final String OP_CREAR_PEDIDO_SUMMARY = "Crear pedido";
    public static final String OP_CREAR_PEDIDO_DESC = "Crear pedido para solicitar platos de mi preferencia";
    public static final String CREATE_PEDIDO_DESCRIPTION_REQUEST = "Objeto de crear pedido";
    public static final String UPDATE_PEDIDO_DESCRIPTION_REQUEST = "Objeto de actualizar pedido";
    public static final String OP_UPDATE_PEDIDO_SUMMARY = "Actualizar pedido";
    public static final String OP_ASIGNAR_PEDIDO_DESC = "Asigna el pedido a estado en preparacion";
    public static final String OP_NOTIFICAR_PEDIDO_DESC = "Notifica al cliente que el pedido esta listo";



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

    public static final String PAGE_DESCRIPTION = "Número de página (empezando desde 0)";
    public static final String PAGE_SIZE_DESCRIPTION = "Tamaño de página";
    public static final String PAGE = "0";
    public static final String NAME_PAGE =  "page";
    public static final String PAGE_SIZE = "10";
}
