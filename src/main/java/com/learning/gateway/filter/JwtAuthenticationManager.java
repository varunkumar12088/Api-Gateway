package com.learning.gateway.filter;

import com.learning.gateway.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationManager.class);
    private final JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
         LOGGER.debug("JwtAuthenticationManager is invoked for authentication: {}", authentication);
            String authToken = authentication.getCredentials().toString();
           try {

               if (jwtUtil.validateToken(authToken)) {
                   String username = jwtUtil.extractUsername(authToken);
                   String role = jwtUtil.extractUserRole(authToken);
                   return Mono.just(new UsernamePasswordAuthenticationToken(username, null, List.of(() -> role)));
               }
           }catch (Exception e) {
               LOGGER.error("JWT token validation failed: {}", e);
               return Mono.error(new RuntimeException("Invalid JWT token"));
           }
            return Mono.empty();
    }
}
