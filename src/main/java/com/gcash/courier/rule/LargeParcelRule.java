package com.gcash.courier.rule;

import com.gcash.courier.config.CourierConfig;
import com.gcash.courier.delivery.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LargeParcelRule implements Rule{
    private final CourierConfig config;

    @Autowired
    public LargeParcelRule(CourierConfig config) {
        this.config = config;
    }

    @Override
    public boolean match(Parcel parcel) {
        return true;
    }

    @Override
    public BigDecimal calculateCost(Parcel parcel) {
        return config.getLargePrice().multiply(BigDecimal.valueOf(getVolume(parcel)));
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
