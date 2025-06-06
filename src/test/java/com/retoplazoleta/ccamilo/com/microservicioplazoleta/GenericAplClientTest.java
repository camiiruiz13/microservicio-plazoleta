package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorClientExeption;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.impl.GenericAplClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.*;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class GenericAplClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GenericAplClient genericAplClient;

    @Test
    @Order(1)
    void testSendRequestSuccess() {
        String url = "http://localhost/api";
        HttpMethod method = HttpMethod.POST;
        Object payload = new Object();
        String token = "Bearer token";

        ObjectMapper objectMapper = new ObjectMapper(); // 🔹 inyectas tú mismo
        GenericAplClient client = new GenericAplClient(restTemplate, objectMapper);

        GenericResponseDTO<Object> expectedResponse = new GenericResponseDTO<>();
        ResponseEntity<GenericResponseDTO> response = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq(url), eq(method), any(HttpEntity.class), eq(GenericResponseDTO.class)))
                .thenReturn(response);

        GenericResponseDTO<Object> result = client.sendRequest(url, method, payload, token, Object.class);

        assertEquals(expectedResponse, result);
        verify(restTemplate).exchange(eq(url), eq(method), any(HttpEntity.class), eq(GenericResponseDTO.class));
    }

    @Test
    @Order(2)
    void testSendRequestHttpClientErrorException() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GenericResponseDTO.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        ErrorClientExeption ex = assertThrows(ErrorClientExeption.class, () ->
                genericAplClient.sendRequest("url", HttpMethod.GET, null, "token", Object.class));

        assertTrue(ex.getMessage().contains(HTTP_ERROR.getMessage()));
    }

    @Test
    @Order(3)
    void testSendRequestHttpServerErrorException() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GenericResponseDTO.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ErrorClientExeption ex = assertThrows(ErrorClientExeption.class, () ->
                genericAplClient.sendRequest("url", HttpMethod.GET, null, "token", Object.class));

        assertTrue(ex.getMessage().contains(HTTP_ERROR.getMessage()));
    }

    @Test
    @Order(4)
    void testSendRequestResourceAccessException() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GenericResponseDTO.class)))
                .thenThrow(new ResourceAccessException("Timeout"));

        ErrorClientExeption ex = assertThrows(ErrorClientExeption.class, () ->
                genericAplClient.sendRequest("url", HttpMethod.GET, null, "token", Object.class));

        assertTrue(ex.getMessage().contains(ACCES_EXCEPTION.getMessage()));
    }

    @Test
    @Order(5)
    void testSendRequestRestClientException() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GenericResponseDTO.class)))
                .thenThrow(new RestClientException("Error"));

        ErrorClientExeption ex = assertThrows(ErrorClientExeption.class, () ->
                genericAplClient.sendRequest("url", HttpMethod.GET, null, "token", Object.class));

        assertTrue(ex.getMessage().contains(REST_CLIENT_EXCEPTION.getMessage()));
    }
}