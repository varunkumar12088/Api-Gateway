package com.learning.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationConverter.class);

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        LOGGER.debug("JwtAuthenticationConverter is invoked for request: {}", exchange.getRequest().getURI());
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            return Mono.just(new UsernamePasswordAuthenticationToken(authToken, authToken));
        }
        return Mono.empty();
    }

}
