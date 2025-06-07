package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.handler;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.ACCES_DENIED;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String ERROR = "error";
    private final String CODIGO = "codigo";
    private final String RUTA = "ruta";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(ERROR, ACCES_DENIED.getMessage());
        errorResponse.put(CODIGO, HttpStatus.FORBIDDEN.value());
        errorResponse.put(RUTA, request.getRequestURI());

        ResponseUtils.write(response, errorResponse, HttpStatus.FORBIDDEN.value());

    }
}
