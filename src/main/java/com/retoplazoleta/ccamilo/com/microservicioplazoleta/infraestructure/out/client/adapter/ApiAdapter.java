package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.request.TraceLog;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.Role;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.RoleCode;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.RoleException;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.*;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.RESTAURANTE_ROLE_EXCEPTION;

@RequiredArgsConstructor
public class ApiAdapter implements IApiClientPort {

    private final IGenericApiClient loginClient;

    private final ObjectMapper objectMapper;

    @Value("${userServices}")
    private String urlUsers;

    @Value("${servicioNotificaciones}")
    private String urlNotificaciones;

    @Value("${servicioTrazabilty}")
    private String urlTrazabilidad;

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

    @Override
    public User idUser(Long idUser, String token) {
        String url = this.urlUsers + FIND_BY_ID_API.getMessage();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        String finalUrl = builder.buildAndExpand(idUser).toUriString();

        GenericResponseDTO<User> response = loginClient.sendRequest(
                finalUrl,
                HttpMethod.GET,
                null,
                token, User.class);

        User userResponse = response.getObjectResponse();

        return userResponse;
    }

    @Override
    public void notificarUser(String celular, String codigo, String token) {

        String url = this.urlNotificaciones +SEND_NOTIFICATION_ID_USER.getMessage();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(celular, codigo).toUriString();

         loginClient.sendRequest(
                finalUrl,
                HttpMethod.GET,
                null,
                token, Object.class);

    }

    @Override
    public void crearTraza(TraceLog traceLog, String token) {
        String url = this.urlTrazabilidad + CREATE_TRACE.getMessage();
        GenericRequest<TraceLog> request = new GenericRequest<>();
        request.setRequest(traceLog);
        loginClient.sendRequest(
                url,
                HttpMethod.POST,
                request,
                token,
                Void.class
        );
    }

    @Override
    public List<User> fetchEmployeesAndClients(List<Long> clientIds, List<Long> chefIds, String token) {
        String url = this.urlUsers + FIND_BY_IDS_API.getMessage();

        Map<String, Object> innerRequest = Map.of(
                "clientIds", clientIds,
                "chefIds", chefIds
        );
        Map<String, Object> body = Map.of("request", innerRequest);

        GenericResponseDTO<Object> resp = loginClient.sendRequest(
                url,
                HttpMethod.POST,
                body,
                token,
                Object.class
        );

        Object rawObj = resp.getObjectResponse();
        if (!(rawObj instanceof List)) {
            return List.of();
        }
        @SuppressWarnings("unchecked")
        List<Object> rawList = (List<Object>) rawObj;
        return rawList.stream()
                .map(item -> objectMapper.convertValue(item, User.class))
                .toList();
    }

}
