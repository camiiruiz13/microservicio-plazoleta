package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public String message;
    private T objectResponse;
    public int statusCode;

}
