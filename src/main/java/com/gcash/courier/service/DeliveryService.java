package com.gcash.courier.service;

import com.gcash.courier.delivery.model.DeliveryCost;
import com.gcash.courier.delivery.model.Parcel;
import reactor.core.publisher.Mono;

public interface DeliveryService {
    Mono<DeliveryCost> calculateDeliveryCost(Parcel parcel);
}
