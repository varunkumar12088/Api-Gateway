package com.learning.gateway.constatnt;

import java.util.Arrays;
import java.util.List;

public class AuthConstant {

    public static final List<String> EXCLUDE_URI = Arrays.asList(
        "/api/v1/auth/login",
        "/api/v1/auth/register",
        "/actuator/**",
        "/health"
    );

}
