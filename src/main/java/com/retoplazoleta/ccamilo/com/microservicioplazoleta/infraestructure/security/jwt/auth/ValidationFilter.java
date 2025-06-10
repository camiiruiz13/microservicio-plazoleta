package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.UserDTOResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IApiClientPort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.input.rest.dto.GenericResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.client.IGenericApiClient;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.auth.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.FIND_BY_CORREO_API;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_INVALID;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_VENCIDO;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.shared.ResponseUtils.write;

@Slf4j
public class ValidationFilter extends BasicAuthenticationFilter {



    private final IApiClientPort apiClientPort;
    private final ObjectMapper objectMapper;
    @Value("${userServices}")
    private  String urlUsers;

    public ValidationFilter(AuthenticationManager authManager, IApiClientPort apiClientPort, ObjectMapper objectMapper) {
        super(authManager);
        this.apiClientPort = apiClientPort;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            Date expiration = decodedJWT.getExpiresAt();
            if (expiration != null && isTokenExpired(expiration)) {
                write(response, TOKEN_VENCIDO.getMessage(), HttpStatus.UNAUTHORIZED.value());
                return;
            }


            String correo = decodedJWT.getSubject();
            String rawAuthorities = decodedJWT.getClaim("authorities").asString();


            List<Map<String, String>> rolesList = objectMapper.readValue(
                    rawAuthorities,
                    new TypeReference<List<Map<String, String>>>() {}
            );
            List<SimpleGrantedAuthority> authorities = rolesList.stream()
                    .map(roleMap -> new SimpleGrantedAuthority(roleMap.get("authority")))
                    .toList();


            Long idUser = apiClientPort.idPropietario(correo, token);
            AuthenticatedUser userAuthenticate = new AuthenticatedUser(
                    idUser.toString(),
                    correo,
                    null,
                    authorities
            );
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userAuthenticate, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("Error en validación de token: {}", ex.getMessage());
            write(response, TOKEN_INVALID.getMessage()+ ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        }

    }



    private boolean isTokenExpired(Date exp) {
        return exp.before(new Date());
    }


}