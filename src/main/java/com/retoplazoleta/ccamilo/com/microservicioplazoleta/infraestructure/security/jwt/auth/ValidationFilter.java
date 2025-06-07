package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.RoleDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.UserDTOResponse;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.commons.constants.ApiClient.FIND_BY_CORREO_API;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_INVALID;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.exception.ErrorException.TOKEN_VENCIDO;
import static com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.util.ResponseUtils.write;

@Slf4j
public class ValidationFilter extends BasicAuthenticationFilter {


    private final IGenericApiClient loginClient;
    @Value("${userServices}")
    private  String urlUsers;

    public ValidationFilter(AuthenticationManager authManager, IGenericApiClient loginClient) {
        super(authManager);
        this.loginClient = loginClient;
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
            if (isTokenExpired(decodedJWT.getExpiresAt())) {
                write(response, TOKEN_VENCIDO.getMessage(), HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String correo = decodedJWT.getSubject();

            UserDTOResponse usuario = consultarUsuarioPorCorreo(correo, header);

            Long idUser = usuario.getIdUsuario();
            RoleDTO rol = usuario.getRol();
            String nombre = rol.getNombre();
            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + nombre));
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

    private UserDTOResponse consultarUsuarioPorCorreo(String correo, String token) {
        String url = this.urlUsers + FIND_BY_CORREO_API;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(correo).toUriString();

        GenericResponseDTO<UserDTOResponse> response = loginClient.sendRequest(
                finalUrl,
                HttpMethod.GET,
                null,
                token  );

        return response.getObjectResponse();
    }

    private boolean isTokenExpired(Date exp) {
        return exp.before(new Date());
    }


}