package com.gcash.courier.service;

import com.gcash.courier.delivery.model.DeliveryCost;
import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.rule.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    private final RuleEngine ruleEngine;

    private final VoucherService voucherService;

    @Autowired
    public DeliveryServiceImpl(RuleEngine ruleEngine, VoucherService voucherService) {

        this.ruleEngine = ruleEngine;
        this.voucherService = voucherService;
    }


    @Override
    public Mono<DeliveryCost> calculateDeliveryCost(Parcel parcel) {


        return voucherService.discount(parcel.voucherCode()).map( discount -> {
            BigDecimal cost = ruleEngine.applyRules(parcel);
            if ( discount > 0f ) {
                return cost.subtract(
                        cost.multiply(
                                BigDecimal.valueOf(discount)
                                        .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP)
                        )
                );
            }
            return cost;

        }).map(DeliveryCost::new);


    }
}
