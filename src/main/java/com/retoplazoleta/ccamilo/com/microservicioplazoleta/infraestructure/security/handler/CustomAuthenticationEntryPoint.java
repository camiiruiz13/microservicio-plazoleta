package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.handler;



import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_INVALID;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR = "error";
    private static final String CODIGO = "codigo";
    private static final String MENSAJE = "mensaje";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        Map<String, Object> error = new HashMap<>();
        error.put(MENSAJE, TOKEN_INVALID.getMessage());
        error.put(CODIGO, HttpStatus.UNAUTHORIZED.value());
        error.put(ERROR, authException.getMessage());


        ResponseUtils.write(response, error, HttpStatus.UNAUTHORIZED.value());
    }
}
