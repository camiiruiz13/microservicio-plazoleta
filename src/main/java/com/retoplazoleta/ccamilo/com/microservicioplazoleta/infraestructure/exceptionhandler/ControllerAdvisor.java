package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exceptionhandler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.CategoriaValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.PlatoValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorClientExeption;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.RoleException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.TokenInvalidoException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.*;


@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

    public static final String ERROR = "error";


    @ExceptionHandler(ErrorClientExeption.class)
    public ResponseEntity<GenericResponseDTO<Void>> handleDomainException(ErrorClientExeption ex) {
        log.error("Error en la solicitud", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RestauranteValidationException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> restauranteDomainException(RestauranteValidationException ex) {
        log.error("Error al crear un restaurante", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(RESTAURANT_VALIDATION.getMessage(),Map.of(ERROR, ex.getMessage()),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> rolePropietarioException(RoleException ex) {
        log.error("No se puede crear restaurante con rol dioferente a propietario", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(RESTAURANTE_ROLE_EXCEPTION.getMessage(),Map.of(ERROR, ex.getMessage()),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(PlatoValidationException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> platoDomainException(PlatoValidationException ex) {
        log.error("Error a crear un plato", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(PLATO_VALIDATION.getMessage(),Map.of(ERROR, ex.getMessage()),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaValidationException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> categoriaDomainException(CategoriaValidationException ex) {
        log.error("No existen categorias a registrar", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(CATEGORIA_VALIDATION.getMessage(),Map.of(ERROR, ex.getMessage()),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> handleTokenInvalidException(TokenInvalidoException ex) {
        return new ResponseEntity<>(
                ResponseUtils.buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED
        );
    }




}
