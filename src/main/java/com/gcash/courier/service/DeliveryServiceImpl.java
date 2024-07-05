package com.gcash.courier.service;

import com.gcash.courier.delivery.model.DeliveryCost;
import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.rule.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    private final RuleEngine ruleEngine;

    @Autowired
    public DeliveryServiceImpl(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }


    @Override
    public Mono<DeliveryCost> calculateDeliveryCost(Parcel parcel) {
        return Mono.just(new DeliveryCost(ruleEngine.applyRules(parcel)));
    }
}
