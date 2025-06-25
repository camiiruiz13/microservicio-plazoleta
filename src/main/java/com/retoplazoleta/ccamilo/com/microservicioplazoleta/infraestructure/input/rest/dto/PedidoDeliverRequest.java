package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.request.PedidoDeliverDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoDeliverRequest extends GenericRequest<PedidoDeliverDTO> {

    public PedidoDeliverRequest(PedidoDeliverDTO request) {
        super(request);
    }
}
