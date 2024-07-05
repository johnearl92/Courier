package com.gcash.courier.config;

import com.gcash.courier.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ThrottlingFilter implements WebFilter {
    @Autowired
    BucketService bucketService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final var apiKey = exchange.getRequest().getHeaders().getFirst("X-api-key");
        if (apiKey == null || apiKey.isEmpty()) {

            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap("missing X-api-key".getBytes());

            return response.writeWith(Mono.just(dataBuffer));
        }

        final var tokenBucket = bucketService.getBucket(apiKey);
        final var probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            exchange.getResponse().getHeaders().add("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return chain.filter(exchange);
        } else {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap("too many requests".getBytes());

            return response.writeWith(Mono.just(dataBuffer));
        }
    }
}
