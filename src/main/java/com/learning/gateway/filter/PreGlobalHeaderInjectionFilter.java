package com.learning.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreGlobalHeaderInjectionFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreGlobalHeaderInjectionFilter.class);
    private static final String X_TRACKING_ID = "X-Tracking-Id";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LOGGER.debug("PreGlobalHeaderInjectionFilter is invoked for request: {}", exchange.getRequest().getURI());
        // Injecting a custom header into the request
        String trackingId = exchange.getRequest().getHeaders().getFirst(X_TRACKING_ID);
        if(StringUtils.isBlank(trackingId)){
            trackingId = UUID.randomUUID().toString();
        }

        MDC.put(X_TRACKING_ID, trackingId);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(X_TRACKING_ID, trackingId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();
        LOGGER.debug("Injected header {} with value {} into the request", X_TRACKING_ID, trackingId);
        return chain.filter(mutatedExchange);
    }
}
