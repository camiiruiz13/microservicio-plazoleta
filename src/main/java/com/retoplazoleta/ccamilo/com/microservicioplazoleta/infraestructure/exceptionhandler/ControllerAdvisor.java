package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exceptionhandler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception.RestauranteValidationException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorClientExeption;
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

    private final String ERROR = "error";

    @ExceptionHandler(ErrorClientExeption.class)
    public ResponseEntity<GenericResponseDTO<Void>> handleDomainException(ErrorClientExeption ex) {
        log.error("Error en la solicitud", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RestauranteValidationException.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> restauranteDomainException(RestauranteValidationException ex) {
        log.error("Error en la validacion del netocion", ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(RESTAURANT_VALIDATION.getMessage(),Map.of(ERROR, ex.getMessage()),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDTO<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error( GENERIC_EXCEPTION.getMessage() + "{}", ex.getMessage(), ex);
        return new ResponseEntity<>(ResponseUtils.buildResponse(GENERIC_EXCEPTION.getMessage(), Map.of(ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
