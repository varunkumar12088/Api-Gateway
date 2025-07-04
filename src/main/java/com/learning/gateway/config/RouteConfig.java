package com.learning.gateway.config;

import com.learning.gateway.pojo.ServiceRoute;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "gateway")
@Component
public class RouteConfig {

    private List<ServiceRoute> routes;

    public List<ServiceRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<ServiceRoute> routes) {
        this.routes = routes;
    }
}
