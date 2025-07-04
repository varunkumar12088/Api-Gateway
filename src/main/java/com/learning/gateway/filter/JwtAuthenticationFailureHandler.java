package com.learning.gateway.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class JwtAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFailureHandler.class);

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange exchange, AuthenticationException exception) {
        LOGGER.error("Authentication failed: {}", exception.getMessage());
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("Content-Type", "application/json");

        byte[] bytes = "{\"error\": \"Unauthorized or Invalid Token\"}".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        // Log the error message
        LOGGER.debug("Sending error response: {}", new String(bytes, StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
