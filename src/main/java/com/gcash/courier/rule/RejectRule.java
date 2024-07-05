package com.gcash.courier.rule;

import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.exception.ParcelException;
import org.springframework.stereotype.Component;

@Component
public class RejectRule implements Rule{
    @Override
    public boolean match(Parcel parcel) {
        return parcel.weight() > 50;
    }

    @Override
    public double calculateCost(Parcel parcel) {
        throw new ParcelException("Rejected: Weight exceeds 50kg");
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
