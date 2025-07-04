package com.learning.gateway.filter;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setServerAuthenticationConverter(new JwtAuthenticationConverter());
        setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    }
}
