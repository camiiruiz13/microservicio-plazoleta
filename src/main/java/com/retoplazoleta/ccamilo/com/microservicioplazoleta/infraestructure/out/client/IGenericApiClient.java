package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import org.springframework.http.HttpMethod;

import java.util.LinkedHashMap;

public interface IGenericApiClient {

    <T> GenericResponseDTO<T> sendRequest(String url, HttpMethod method, Object payload, String token);
}
