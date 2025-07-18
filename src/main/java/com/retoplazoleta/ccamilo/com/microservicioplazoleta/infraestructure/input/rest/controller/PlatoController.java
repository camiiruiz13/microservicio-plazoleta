package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PlatoDTOResponse;
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
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants.*;


@RestController
@RequestMapping(EndpointApi.BASE_PATH_PLATO)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PLATO, description = SwaggerConstants.TAG_PLATO_DESC)
public class PlatoController {

    private final IPlatoHandler platoHandler;

    @PostMapping(EndpointApi.CREATE_PLATO)
    @PreAuthorize("hasRole('ROLE_PROPIETARIO')")
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
                                                               @AuthenticationPrincipal AuthenticatedUser user) {

        PlatoDTO platoDTO = platoRequest.getRequest();
        platoDTO.setIdPropietario(Long.valueOf(user.getIdUser()));
        platoHandler.savePlato(platoDTO);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO_SUCCES.getMessage(), HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping(EndpointApi.UPDATE_PLATO)
    @PreAuthorize("hasAuthority('ROLE_PROPIETARIO')")
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
                                                                     @AuthenticationPrincipal AuthenticatedUser user) {


        platoHandler.updatePlato(platoRequest.getRequest(), id, Long.valueOf(user.getIdUser()));
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO_UPDATE_SUCCES.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping(EndpointApi.DISABLE_PLATO)
    @PreAuthorize("hasAuthority('ROLE_PROPIETARIO')")
    @Operation(
            summary = SwaggerConstants.OP_DISABLE_PLATO_DESC,
            description = SwaggerConstants.OP_DISABLE_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> activarDesactivarPlato(@PathVariable("idPlato")
                                                                           @Parameter(description = EndpointApi.DISABLE_PLATO, required = true)
                                                                           Long id,
                                                                           @RequestParam
                                                                           @Parameter(
                                                                                   description = OP_DISABLE_DESCRIPTION,
                                                                                   required = true,
                                                                                   example = OP_DISABLE_EXAMPLE,
                                                                                   schema = @Schema(type = BOOLEAN_TYPE, allowableValues = {OP_DISABLE_EXAMPLE_TRUE, OP_DISABLE_EXAMPLE})
                                                                           )
                                                                           Boolean activo,
                                                                           @AuthenticationPrincipal AuthenticatedUser user) {


        platoHandler.updatePlatoDisable(id, activo, Long.valueOf(user.getIdUser()));
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO.getMessage() + (activo ? HABILITADO.getMessage() : DESHABILITADO.getMessage()) + SUCCES_DISABLE.getMessage(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @GetMapping(EndpointApi.FIND_ALL_PLATOS)
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
    @Operation(
            summary = SwaggerConstants.OP_LISTAR_PLATO_SUMMARY,
            description = SwaggerConstants.OP_LISTAR_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<PageResponseDTO<PlatoDTOResponse>>> listarPlatos(
            @PathVariable("idRestaurante")
            @Parameter(description = OP_FILTER_ID_RESTAURANTE, required = true, example = EXAMPLES_ID)
            Long idRestaurante,

            @Parameter(
                    in = ParameterIn.QUERY,
                    description = OP_FILTER_ID_CATEGORIA,
                    example = EXAMPLES_ID
            )
            @RequestParam(required = false)
            Long idCategoria,

            @Parameter(
                    in = ParameterIn.QUERY,
                    description = PAGE_DESCRIPTION,
                    example = PAGE
            )
            @RequestParam(defaultValue = PAGE)
            int page,

            @Parameter(
                    in = ParameterIn.QUERY,
                    description = PAGE_SIZE_DESCRIPTION,
                    example = PAGE_SIZE
            )
            @RequestParam(defaultValue = PAGE_SIZE)
            int pageSize
    )

    {
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(PLATO_LIST.getMessage(), platoHandler.findByPlatoByRestaurantes(idRestaurante, idCategoria, page, pageSize),
                        HttpStatus.OK),
                HttpStatus.OK
        );
    }


}
