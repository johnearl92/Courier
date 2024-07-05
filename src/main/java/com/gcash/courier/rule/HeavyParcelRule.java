package com.gcash.courier.rule;

import com.gcash.courier.config.CourierConfig;
import com.gcash.courier.delivery.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeavyParcelRule implements Rule{
    private final CourierConfig config;

    @Autowired
    public HeavyParcelRule(CourierConfig config) {
        this.config = config;
    }

    @Override
    public boolean match(Parcel parcel) {
        return parcel.weight() > 10;
    }

    @Override
    public double calculateCost(Parcel parcel) {
        return config.getHeavyPrice() * parcel.weight();
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
