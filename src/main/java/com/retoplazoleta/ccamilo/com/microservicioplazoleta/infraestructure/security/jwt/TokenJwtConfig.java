package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.security.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public interface TokenJwtConfig {


    String PREFIX_TOKEN = "Bearer ";
    String HEADER_AUTHORIZATION = "Authorization";

    String CONTENT_TYPE = "application/json";
}
