package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class GenericAplClient implements IGenericApiClient {
    @Override
    public GenericResponseDTO<LinkedHashMap<?, ?>> sendRequest(String url, HttpMethod method, Object payload) {
        return null;
    }
}
