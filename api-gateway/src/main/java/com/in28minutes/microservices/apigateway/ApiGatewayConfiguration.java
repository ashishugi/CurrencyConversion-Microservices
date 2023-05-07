package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get")
                .filters(f -> f.addRequestHeader("MyHeader" , "MyHeaderValue").addRequestParameter("Param", "ParamValue")) // you can passheader
                .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**") // when i get any url with currency-exchange
                        .uri("lb://currency-exchange") // then talk with eureka and do the load balance between the instances and redirect (we need to removed a property from application.properties
                        )
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion")
                    )
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion")
                )
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion")
                )
                .build();
    }
}
