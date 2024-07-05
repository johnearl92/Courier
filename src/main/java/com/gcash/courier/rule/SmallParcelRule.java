package com.gcash.courier.rule;

import com.gcash.courier.config.CourierConfig;
import com.gcash.courier.delivery.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmallParcelRule implements Rule{
    private final CourierConfig config;

    @Autowired
    public SmallParcelRule(CourierConfig config) {
        this.config = config;
    }

    @Override
    public boolean match(Parcel parcel) {
        double volume = parcel.height() * parcel.width() * parcel.length();
        return volume < 1500;
    }

    @Override
    public double calculateCost(Parcel parcel) {
        double volume = parcel.height() * parcel.width() * parcel.length();
        return config.getSmallPrice() * volume;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}