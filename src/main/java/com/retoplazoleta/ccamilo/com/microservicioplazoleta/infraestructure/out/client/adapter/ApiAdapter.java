package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.Role;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.FIND_BY_CORREO_API;


@RequiredArgsConstructor
public class ApiAdapter implements IApiClientPort{

    private final IGenericApiClient loginClient;

    @Value("${userServices}")
    private  String urlUsers;

    @Override
    public Long idPropietario(String correo, String token) {
        String url = this.urlUsers + FIND_BY_CORREO_API;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(correo).toUriString();

        GenericResponseDTO<User> response = loginClient.sendRequest(
                finalUrl,
                HttpMethod.GET,
                null,
                token  );

        User userResponse = response.getObjectResponse();
        return userResponse.getIdUsuario();
    }
}
