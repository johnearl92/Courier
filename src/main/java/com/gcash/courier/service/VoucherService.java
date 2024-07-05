package com.gcash.courier.service;

import reactor.core.publisher.Mono;


public interface VoucherService {
    Mono<Float> discount(String voucher);
}
