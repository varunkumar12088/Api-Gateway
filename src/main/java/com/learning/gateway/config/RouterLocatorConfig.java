package com.learning.gateway.config;

import com.learning.gateway.domain.ServiceRoute;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterLocatorConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RouteConfig config) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        for (ServiceRoute route : config.getRoutes()) {
            if (route.isEnabled()) {
                routes.route(route.getId(), r -> r
                        .path(route.getPath())
                        .uri(route.getUri()));
            }
        }

        return routes.build();
    }


}
