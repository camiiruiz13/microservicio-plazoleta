package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoRequest extends GenericRequest<PedidoDTO> {

    public PedidoRequest(PedidoDTO request) {
        super(request);
    }
}
