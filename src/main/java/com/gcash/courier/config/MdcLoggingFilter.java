package com.gcash.courier.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class MdcLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestUri = exchange.getRequest().getURI().toString();
        String requestMethod = exchange.getRequest().getMethod().toString();
        MDC.put("REQUEST_URI", requestUri);
        MDC.put("REQUEST_METHOD", requestMethod);
        MDC.put("TRACE_ID", UUID.randomUUID().toString());
        return chain.filter(exchange)
                .doFinally(signalType -> MDC.clear());
    }
}
