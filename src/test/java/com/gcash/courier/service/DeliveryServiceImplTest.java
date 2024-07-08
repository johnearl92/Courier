package com.gcash.courier.service;

import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.rule.RuleEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @InjectMocks
    DeliveryServiceImpl deliveryService;

    @Mock
    VoucherService voucherService;

    @Mock
    RuleEngine ruleEngine;

    @Test
    void shouldCalculateDeliveryCostWithDiscount() {
        // given
        when(voucherService.discount(anyString())).thenReturn(Mono.just(50f));
        when(ruleEngine.applyRules(any())).thenReturn(BigDecimal.valueOf(80));

        // when
        final var cost = deliveryService.calculateDeliveryCost(new Parcel(1.0f, 20.0f, 10.0f, 10.0f, "test"));

        // then
        assertEquals(0, BigDecimal.valueOf(40).compareTo(cost.block().cost()));
    }
}