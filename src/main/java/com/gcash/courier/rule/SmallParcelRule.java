package com.gcash.courier.rule;

import com.gcash.courier.config.CourierConfig;
import com.gcash.courier.delivery.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SmallParcelRule implements Rule{
    private final CourierConfig config;

    @Autowired
    public SmallParcelRule(CourierConfig config) {
        this.config = config;
    }

    @Override
    public boolean match(Parcel parcel) {
        return getVolume(parcel) < 1500;
    }

    @Override
    public BigDecimal calculateCost(Parcel parcel) {
        return config.getSmallPrice().multiply(BigDecimal.valueOf(getVolume(parcel)));
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
