package com.alquiler.gateway.swagger;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {



    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("/user-service/**"),
                        HandlerFunctions.http("http://localhost:8081/user-service"))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("user-service-swagger")
                .route(RequestPredicates.path("/aggregate/user-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> vehicleServiceRoutes() {
        return GatewayRouterFunctions.route("vehicle-service")
                .route(RequestPredicates.path("/vehicle-service/**"),
                        HandlerFunctions.http("http://localhost:8082/vehicle-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> vehicleServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("vehicle-service-swagger")
                .route(RequestPredicates.path("/aggregate/vehicle-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayServiceRoutes() {
        return GatewayRouterFunctions.route("gateway-service")
                .route(RequestPredicates.path("/gateway-service/**"),
                        HandlerFunctions.http("http://localhost:8080/gateway-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("gateway-service-swagger")
                .route(RequestPredicates.path("/aggregate/gateway-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoutes() {
        return GatewayRouterFunctions.route("booking-service")
                .route(RequestPredicates.path("/booking-service/**"),
                        HandlerFunctions.http("http://localhost:8083/booking-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("booking-service-swagger")
                .route(RequestPredicates.path("/aggregate/booking-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8083"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
