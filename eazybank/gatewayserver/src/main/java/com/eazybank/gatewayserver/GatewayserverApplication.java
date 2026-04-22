package com.eazybank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


    @Bean
    public RouteLocator routeLocatorBuildconfig(RouteLocatorBuilder routebuilder){

        return routebuilder.routes()
                .route(p->p
                        .path("/eazybank/accounts/**")
                        .filters(f->f.rewritePath("/eazybank/accounts/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forword:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .route(p->p
                        .path("/eazybank/loans/**")
                       .filters(f->f.rewritePath("/eazybank/loans/(?<segment>.*)","/${segment}")
                        .addResponseHeader("X-Response-Time", LocalTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(p->p
                        .path("/eazybank/card/**")
                        .filters(f->f.rewritePath("/eazybank/card/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalTime.now().toString()))
                        .uri("lb://CARDS")).build();
    }
}
