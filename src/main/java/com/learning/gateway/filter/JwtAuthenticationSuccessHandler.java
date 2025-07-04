package com.learning.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

public class JwtAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationSuccessHandler.class);

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        LOGGER.debug("Authentication successful for user: {}", authentication.getName());
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

        // Example: Add a custom header
        response.getHeaders().add("X-Auth-Status", "Success");
        LOGGER.debug("Added custom header X-Auth-Status with value 'Success' to the response");
        // Continue filter chain
        return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
    }
}
