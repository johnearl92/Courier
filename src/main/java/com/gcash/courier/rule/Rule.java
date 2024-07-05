package com.gcash.courier.rule;

import com.gcash.courier.delivery.model.Parcel;

import java.math.BigDecimal;

public interface Rule {
    boolean match(Parcel parcel);
    BigDecimal calculateCost(Parcel parcel);
    int getPriority();

    default double getVolume(
            Parcel parcel
    ) {
        return parcel.height() * parcel.width() * parcel.length();
    }
}
