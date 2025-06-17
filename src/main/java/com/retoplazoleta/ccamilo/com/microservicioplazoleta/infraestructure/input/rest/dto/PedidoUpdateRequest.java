package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoUpdateRequest extends GenericRequest<PedidoUpdateDTO> {

    public PedidoUpdateRequest(PedidoUpdateDTO request) {
        super(request);
    }
}
