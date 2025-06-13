package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPedidoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PedidoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.EndpointApi;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig.CONTENT_TYPE;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.PEDIDO_SUCCES;

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




}
