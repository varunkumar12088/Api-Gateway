
package com.learning.gateway.config;

import com.learning.gateway.filter.JwtAuthenticationFilter;
import com.learning.gateway.filter.JwtHeaderAugmentationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtAuthenticationFilter authenticationFilter;

    private final JwtHeaderAugmentationFilter jwtHeaderAugmentationFilter;

    public SecurityConfig(JwtAuthenticationFilter authenticationFilter, JwtHeaderAugmentationFilter jwtHeaderAugmentationFilter) {
        this.authenticationFilter = authenticationFilter;
        this.jwtHeaderAugmentationFilter = jwtHeaderAugmentationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOGGER.debug("Configuring security for the API Gateway");
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .pathMatchers("/actuator/**", "/health").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(jwtHeaderAugmentationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
