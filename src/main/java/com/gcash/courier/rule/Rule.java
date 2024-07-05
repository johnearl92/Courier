package com.gcash.courier.rule;

import com.gcash.courier.delivery.model.Parcel;

public interface Rule {
    boolean match(Parcel parcel);
    double calculateCost(Parcel parcel);
    int getPriority();
}
