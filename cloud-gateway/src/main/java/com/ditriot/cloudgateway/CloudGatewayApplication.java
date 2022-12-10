package com.ditriot.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@CrossOrigin("*")
//@EnableDiscoveryClient
//@EnableConfigurationProperties
public class CloudGatewayApplication {
 //@Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/RH/**").uri("lb://RH-SERVICE"))
                .route(r->r.path("/business/**").uri("lb://BUSINESS-SERVICE"))
                .route(r->r.path("/api/**").uri("lb://SECURITY-SERVICE"))
                .build();
    }
    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }


    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

}
