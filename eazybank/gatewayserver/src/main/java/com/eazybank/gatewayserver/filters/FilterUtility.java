package com.eazybank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.logging.Logger;

@Component
public class FilterUtility {

    static final String CORRELATION_ID = "eazybank-correlation-id";

    public String getCorrelationId(HttpHeaders requestheaders){
        if(requestheaders.get(CORRELATION_ID) != null){
            List<String>requestHeaderList  = requestheaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        }else{
            return null;
        }
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange,String name,String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange,String correlationId){
        return this.setCorrelationId(exchange,CORRELATION_ID,correlationId);
    }
}
