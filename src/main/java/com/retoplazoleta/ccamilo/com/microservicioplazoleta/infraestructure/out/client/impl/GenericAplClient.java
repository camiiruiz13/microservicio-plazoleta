package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorClientExeption;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.*;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.*;

@RequiredArgsConstructor
public class GenericAplClient implements IGenericApiClient {


    private final RestTemplate restTemplate;

    @Override
    public GenericResponseDTO<?> sendRequest(String url, HttpMethod method, Object payload, String token) {
        try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Object> entity = (payload != null)
                ? new HttpEntity<>(payload, headers)
                : new HttpEntity<>(headers);


        ResponseEntity<GenericResponseDTO> responseEntity =
                restTemplate.exchange(url, method, entity, GenericResponseDTO.class);

        GenericResponseDTO<?> body = responseEntity.getBody();

        return body;

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ErrorClientExeption(HTTP_ERROR.getMessage() + " " + ex.getStatusCode(), ex);
        } catch (ResourceAccessException ex) {
            throw new ErrorClientExeption(ACCES_EXCEPTION.getMessage()+ ex.getMessage(), ex);
        } catch (RestClientException ex) {
            throw new ErrorClientExeption(REST_CLIENT_EXCEPTION.getMessage() + ex.getMessage(), ex);
        }


    }
}
