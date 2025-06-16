package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PedidoDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig.CONTENT_TYPE;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.PEDIDO_LIST;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.PEDIDO_SUCCES;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants.*;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_PEDIDO)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PEDIDO, description = SwaggerConstants.TAG_PEDIDO_DESC)
public class Pedidoontroller {

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
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PEDIDO_LIST.getMessage(), pedidoHandler.findByEstadoAndRestauranteId(estado , idRestaurante, Long.valueOf(user.getIdUser()), page, pageSize),
                        HttpStatus.OK),
                HttpStatus.OK
        );
    }


}
