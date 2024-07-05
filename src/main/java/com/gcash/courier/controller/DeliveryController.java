package com.gcash.courier.controller;

import com.gcash.courier.delivery.model.DeliveryCost;
import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/calculate")
    public Mono<DeliveryCost> calculate( @RequestBody Parcel parcel) {
        return deliveryService.calculateDeliveryCost(parcel);
    }
}
