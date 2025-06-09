package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IPlatoHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PlatoRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.PlatoUpdateRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.EndpointApi;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.PLATO_SUCCES;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.PLATO_UPDATE_SUCCES;


@RestController
@RequestMapping(EndpointApi.BASE_PATH_PLATO)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PLATO, description = SwaggerConstants.TAG_PLATO_DESC)
public class PlatoController {

    private final IPlatoHandler platoHandler;

    @PostMapping(EndpointApi.CREATE_PLATO)
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_PLATO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> crearPlato(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                             description = SwaggerConstants.CREATE_PLATO_DESCRIPTION_REQUEST,
                                                                             content = @Content(
                                                                                     mediaType = CONTENT_TYPE,
                                                                                     schema = @Schema(implementation = PlatoRequest.class)))
                                                                     @RequestBody PlatoRequest platoRequest,
                                                               @AuthenticationPrincipal AuthenticatedUser user){


        platoHandler.savePlato(platoRequest.getRequest(), Long.valueOf(user.getIdUser()));
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO_SUCCES.getMessage(), HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping(EndpointApi.UPDATE_PLATO)
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = SwaggerConstants.OP_UPDATE_PLATO_SUMMARY,
            description = SwaggerConstants.OP_UPDATE_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> actuaalizarPlato(@PathVariable("idPlato")
                                                                         @Parameter(description = EndpointApi.UPDATE_PLATO, required = true)
                                                                         Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                       description = SwaggerConstants.UPDATE_PLATO_DESCRIPTION_REQUEST,
                                                                       content = @Content(
                                                                               mediaType = CONTENT_TYPE,
                                                                               schema = @Schema(implementation = PlatoUpdateRequest.class)))
                                                               @RequestBody PlatoUpdateRequest platoRequest,
                                                               @AuthenticationPrincipal AuthenticatedUser user){


        platoHandler.updatePlato(platoRequest.getRequest(), id, Long.valueOf(user.getIdUser()));
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }



}
