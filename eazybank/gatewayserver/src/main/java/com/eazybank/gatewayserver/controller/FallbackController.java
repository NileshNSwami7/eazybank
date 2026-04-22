package com.eazybank.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport(){
        return Mono.just("The service is temporarly  unavailable please try after some time or contact support team.");
    }
}
