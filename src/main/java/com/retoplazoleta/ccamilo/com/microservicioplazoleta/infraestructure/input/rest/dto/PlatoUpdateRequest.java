package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlatoUpdateRequest extends GenericRequest<PlatoDTOUpdate> {

    public PlatoUpdateRequest(PlatoDTOUpdate request) {
        super(request);
    }
}
