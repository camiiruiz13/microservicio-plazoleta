package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoTraceDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoDeliverRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoUpdateRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.EndpointApi;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig.CONTENT_TYPE;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants.*;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_PEDIDO)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PEDIDO, description = SwaggerConstants.TAG_PEDIDO_DESC)
public class PedidoController {

    private final IPedidoHandler pedidoHandler;

    @PostMapping(EndpointApi.CREATE_PEDIDOS)
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> crearPedido(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                        description = SwaggerConstants.CREATE_PEDIDO_DESCRIPTION_REQUEST,
                                                                        content = @Content(
                                                                                mediaType = CONTENT_TYPE,
                                                                                schema = @Schema(implementation = PedidoRequest.class)))
                                                                @RequestBody PedidoRequest pedidoRequest,
                                                                @AuthenticationPrincipal AuthenticatedUser user) {

        PedidoDTO pedidoDTO = pedidoRequest.getRequest();
        pedidoDTO.setIdCliente(Long.valueOf(user.getIdUser()));
        pedidoHandler.savePedido(pedidoDTO);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_SUCCES.getMessage(), HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }


    @GetMapping(EndpointApi.LIST_PEDIDOS_BY_ESTADO)
    @PreAuthorize("hasAuthority('ROLE_EMPLEADO')")
    @Operation(
            summary = SwaggerConstants.OP_LISTAR_RESTAURANTE_SUMMARY,
            description = SwaggerConstants.OP_LISTAR_RESTAURANTE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<PageResponseDTO<PedidoDTOResponse>>> listaDePedidosPorEstado(
            @PathVariable("estado")
            @Parameter(
                    description = OP_ESTADODISABLE_DESCRIPTION,
                    required = true,
                    example = DESC_PENDIENTE,
                    schema = @Schema(type = STRING_TYPE, allowableValues = {DESC_PENDIENTE,
                            DESC_EN_PREPARACION,
                            DESC_ENTREGADO,
                            DESC_CANCELADO,
                            DESC_LISTO})
            )
            String estado,

            @PathVariable("idRestaurante")
            @Parameter(description = OP_FILTER_ID_RESTAURANTE, required = true, example = EXAMPLES_ID)
            Long idRestaurante,

            @Parameter(
                    in = ParameterIn.QUERY,
                    description = PAGE_DESCRIPTION,
                    example = PAGE
            )
            @RequestParam(defaultValue = PAGE) int page,

            @Parameter(
                    in = ParameterIn.QUERY,
                    description = PAGE_SIZE_DESCRIPTION,
                    example = PAGE_SIZE
            )
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize
    ) {
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_LIST.getMessage(), pedidoHandler.findByEstadoAndRestauranteId(estado, idRestaurante, page, pageSize),
                        HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping(EndpointApi.ASIGNAR_PEDIDO)
    @PreAuthorize("hasAuthority('ROLE_EMPLEADO')")
    @Operation(
            summary = OP_UPDATE_PEDIDO_SUMMARY,
            description = OP_ASIGNAR_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> asignarPedido(HttpServletRequest request,
                                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                          description = SwaggerConstants.UPDATE_PEDIDO_DESCRIPTION_REQUEST,
                                                                          content = @Content(
                                                                                  mediaType = CONTENT_TYPE,
                                                                                  schema = @Schema(implementation = PedidoUpdateRequest.class)))
                                                                  @PathVariable
                                                                  @Parameter(description = OP_FILTER_ID_PEDIDO, required = true, example = EXAMPLES_ID)
                                                                  Long id,
                                                                  @RequestBody PedidoUpdateRequest pedidoRequest,
                                                                  @AuthenticationPrincipal AuthenticatedUser user) {

        String token = request.getHeader(HEADER_AUTHORIZATION);
        PedidoUpdateDTO pedidoDTO = pedidoRequest.getRequest();
        pedidoDTO.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoDTO.setCorreoEmpleado(user.getUsername());
        pedidoHandler.asignarPedido(id, pedidoDTO, token);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping(EndpointApi.NOTIFICAR_PEDIDO)
    @PreAuthorize("hasAuthority('ROLE_EMPLEADO')")
    @Operation(
            summary = OP_UPDATE_PEDIDO_SUMMARY,
            description = OP_NOTIFICAR_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> notificarPedido(HttpServletRequest request,
                                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                            description = SwaggerConstants.UPDATE_PEDIDO_DESCRIPTION_REQUEST,
                                                                            content = @Content(
                                                                                    mediaType = CONTENT_TYPE,
                                                                                    schema = @Schema(implementation = PedidoUpdateRequest.class)))
                                                                    @PathVariable
                                                                    @Parameter(description = OP_FILTER_ID_PEDIDO, required = true, example = EXAMPLES_ID)
                                                                    Long id,
                                                                    @RequestBody PedidoUpdateRequest pedidoRequest,
                                                                    @AuthenticationPrincipal AuthenticatedUser user) {

        String token = request.getHeader(HEADER_AUTHORIZATION);
        PedidoUpdateDTO pedidoDTO = pedidoRequest.getRequest();
        pedidoDTO.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoDTO.setCorreoEmpleado(user.getUsername());
        pedidoHandler.notificarPedido(id, pedidoDTO, token);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping(EndpointApi.ENTREGAR_PEDIDO)
    @PreAuthorize("hasAuthority('ROLE_EMPLEADO')")
    @Operation(
            summary = OP_UPDATE_PEDIDO_SUMMARY,
            description = OP_ENTREGAR_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> entregarPedido(HttpServletRequest request,
                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                           description = SwaggerConstants.UPDATE_PEDIDO_DESCRIPTION_REQUEST,
                                                                           content = @Content(
                                                                                   mediaType = CONTENT_TYPE,
                                                                                   schema = @Schema(implementation = PedidoUpdateRequest.class)))
                                                                   @PathVariable
                                                                   @Parameter(description = OP_FILTER_ID_PEDIDO, required = true, example = EXAMPLES_ID)
                                                                   Long id,
                                                                   @RequestBody PedidoDeliverRequest pedidoRequest,
                                                                   @AuthenticationPrincipal AuthenticatedUser user) {

        String token = request.getHeader(HEADER_AUTHORIZATION);
        PedidoDeliverDTO pedidoDTO = pedidoRequest.getRequest();
        pedidoDTO.setIdChef(Long.valueOf(user.getIdUser()));
        pedidoDTO.setCorreoEmpleado(user.getUsername());
        pedidoHandler.entregarPedido(id, pedidoDTO, token);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping(EndpointApi.CANCELAR_PEDIDO)
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    @Operation(
            summary = OP_UPDATE_PEDIDO_SUMMARY,
            description = OP_CANCELAR_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> cancelarPedido(HttpServletRequest request,
                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                           description = SwaggerConstants.UPDATE_PEDIDO_DESCRIPTION_REQUEST,
                                                                           content = @Content(
                                                                                   mediaType = CONTENT_TYPE,
                                                                                   schema = @Schema(implementation = PedidoUpdateRequest.class)))
                                                                   @PathVariable
                                                                   @Parameter(description = OP_FILTER_ID_PEDIDO, required = true, example = EXAMPLES_ID)
                                                                   Long id,
                                                                   @RequestBody PedidoUpdateRequest pedidoRequest,
                                                                   @AuthenticationPrincipal AuthenticatedUser user) {


        String token = request.getHeader(HEADER_AUTHORIZATION);
        PedidoUpdateDTO pedidoDTO = pedidoRequest.getRequest();
        pedidoDTO.setIdCliente(Long.valueOf(user.getIdUser()));
        pedidoDTO.setCorreoCliente(user.getUsername());
        pedidoHandler.cancelarPedido(id, pedidoDTO, token);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @GetMapping(EndpointApi.FILTRAR_PEDIDOS_ID_RESTAURANTE)
    @PreAuthorize("hasRole('ROLE_PROPIETARIO')")
    @Operation(summary = SwaggerConstants.OP_FILTRAR_PEDIDO_RESTAURANTE_SUMMARY,
            description = SwaggerConstants.OP_FILTRA_PEDIDO_REST_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<List<PedidoTraceDTOResponse>>> findPedidoByIdRestaurant(HttpServletRequest request,
                                                                                                     @PathVariable("idRestaurante")
                                                                                                     @Parameter(description = OP_FILTER_ID_RESTAURANTE, required = true, example = EXAMPLES_ID)
                                                                                                     Long idRestaurante,
                                                                                                     @AuthenticationPrincipal AuthenticatedUser user) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_LISTAS_RESTAURANTE_ID.getMessage(), pedidoHandler.findPedidoByIdRestaurant(idRestaurante, Long.valueOf(user.getIdUser()), token),
                        HttpStatus.OK),
                HttpStatus.OK
        );}
}
