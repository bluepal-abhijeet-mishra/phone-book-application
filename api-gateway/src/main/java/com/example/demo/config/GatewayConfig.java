package com.example.demo.config;

import com.example.demo.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(filter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://user-service"))
                .route("contact-service", r -> r.path("/api/contacts/**")
                        .filters(f -> f.filter(filter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://contact-service"))
                .build();
    }
}
