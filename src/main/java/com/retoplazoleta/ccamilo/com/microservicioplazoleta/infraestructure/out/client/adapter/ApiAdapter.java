package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.Role;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.RoleCode;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.RoleException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.FIND_BY_CORREO_API;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.RESTAURANTE_ROLE_EXCEPTION;


@RequiredArgsConstructor
public class ApiAdapter implements IApiClientPort {

    private final IGenericApiClient loginClient;

    @Value("${userServices}")
    private String urlUsers;

    @Override
    public Long idPropietario(String correo, String token) {
       return idPropietario(correo, token, null);
    }

    @Override
    public Long idPropietario(String correo, String token, Object model) {
        String url = this.urlUsers + FIND_BY_CORREO_API.getMessage();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        String finalUrl = builder.buildAndExpand(correo).toUriString();

        GenericResponseDTO<User> response = loginClient.sendRequest(
                finalUrl,
                HttpMethod.GET,
                null,
                token, User.class);

        User userResponse = response.getObjectResponse();
        Role role = userResponse.getRol();
        String roleName = role.getNombre();
        if ((model !=null && model instanceof Restaurante) && !RoleCode.PROPIETARIO.name().equals(roleName))
            throw new RoleException(RESTAURANTE_ROLE_EXCEPTION.getMessage());

        return userResponse.getIdUsuario();
    }
}
