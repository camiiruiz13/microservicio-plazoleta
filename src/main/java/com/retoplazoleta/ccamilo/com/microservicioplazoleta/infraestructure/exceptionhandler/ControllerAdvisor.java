package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exceptionhandler;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorClientExeption;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ErrorClientExeption.class)
    public ResponseEntity<GenericResponseDTO<Void>> handleDomainException(ErrorClientExeption ex) {
        return new ResponseEntity<>(ResponseUtils.buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

    }


}
