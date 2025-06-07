package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.RestauranteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestauranteRequest extends GenericRequest<RestauranteDTO> {

    public RestauranteRequest(RestauranteDTO request) {
        super(request);
    }
}
