package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.controller;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RestauranteDTOPage;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.handler.IRestauranteHandler;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.RestauranteRequest;
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
import org.springframework.web.bind.annotation.*;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.RESTAURANT_LIST;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseMessage.RESTAURANT_SUCCES;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.SwaggerConstants.*;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_RESTAURANTE)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_RESTAURANTE, description = SwaggerConstants.TAG_RESTAURANTE_DESC)
public class RestauranteController {

    private final IRestauranteHandler restauranteHandler;



    @PostMapping(EndpointApi.CREATE_RESTAURANTE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_RESTAURANTE_SUMMARY,
            description = SwaggerConstants.OP_CREAR_RESTAURANTE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerConstants.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerConstants.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerConstants.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerConstants.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerConstants.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerConstants.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<GenericResponseDTO<Void>> crearRestaurante(HttpServletRequest request,
                                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                             description = SwaggerConstants.CREATE_RESTAURANTE_DESCRIPTION_REQUEST,
                                                                             content = @Content(
                                                                                     mediaType = CONTENT_TYPE,
                                                                                     schema = @Schema(implementation = RestauranteRequest.class)))
                                                                     @RequestBody RestauranteRequest restauranteRequest){

        String token =request.getHeader(HEADER_AUTHORIZATION);
        restauranteHandler.saveRestaurante(restauranteRequest.getRequest(), token);
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(RESTAURANT_SUCCES.getMessage(), HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @GetMapping(EndpointApi.FIND_ALL_RESTAURANTE)
    @PreAuthorize("hasAuthority('ROLE_CLIENTE')")
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
    public ResponseEntity<GenericResponseDTO<PageResponseDTO<RestauranteDTOPage>>> listarRestaurantes(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = PAGE_DESCRIPTION ,
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
                ResponseUtils.buildResponse(RESTAURANT_LIST.getMessage(), restauranteHandler.findAllRestaurantes(page, pageSize),
                        HttpStatus.OK),
                HttpStatus.OK
        );
    }





}
