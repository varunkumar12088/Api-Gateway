
package com.learning.gateway.filter;

import com.learning.gateway.constatnt.AuthConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtHeaderAugmentationFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHeaderAugmentationFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LOGGER.debug("JwtHeaderAugmentationFilter is invoked for request: {}", exchange.getRequest().getURI());
        String path = exchange.getRequest().getPath().value();
        if(AuthConstant.EXCLUDE_URI.contains(path)){
            return chain.filter(exchange);
        }
        ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .flatMap(authentication -> {
                    if (authentication != null && authentication.isAuthenticated()) {
                        String username = authentication.getName();
                        String role = authentication.getAuthorities().stream()
                                .findFirst().get().getAuthority();

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Name", username)
                                .header("X-User-Role", role)
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);
                    }
                    return chain.filter(exchange);
                });

        LOGGER.debug("Augmented request headers with user information for path: {}", path);
        return chain.filter(exchange);
    }
}
