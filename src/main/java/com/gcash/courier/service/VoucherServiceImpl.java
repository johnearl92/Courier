package com.gcash.courier.service;

import com.gcash.courier.config.CourierConfig;
import com.gcash.courier.delivery.model.VoucherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class VoucherServiceImpl implements VoucherService{

    private Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final CourierConfig config;
    private final WebClient webClient;

    public VoucherServiceImpl(CourierConfig config, WebClient.Builder builder) {

        this.config = config;
        this.webClient = builder
                .baseUrl(config.getVoucherHost())
                .build();
    }

    @Override
    public Mono<Float> discount(String voucher) {
        logger.info("getting discount");
        return this.webClient.get()
                .uri("/voucher/{voucherCode}?key={apiKey}", voucher, config.getVoucherApiKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(VoucherResponse.class)
                .map(VoucherResponse::discount)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(throwable -> {
                    logger.error("Error fetching discount: {}", throwable.getMessage());
                    return Mono.just(0f);
                });
    }
}

