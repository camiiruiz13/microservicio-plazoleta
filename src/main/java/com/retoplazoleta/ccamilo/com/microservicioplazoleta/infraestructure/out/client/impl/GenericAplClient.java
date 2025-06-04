package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericRequest;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class GenericAplClient implements IGenericApiClient {
    @Override
    public GenericResponseDTO<LinkedHashMap<?, ?>> sendRequest(String url, HttpMethod method, Object payload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity;
        GenericRequest<?> request = new GenericRequest<>();

        return null;
    }
}
