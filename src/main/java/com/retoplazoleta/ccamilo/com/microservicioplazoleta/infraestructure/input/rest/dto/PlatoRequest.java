package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlatoRequest extends GenericRequest<PlatoDTO> {

    public PlatoRequest(PlatoDTO request) {
        super(request);
    }
}
