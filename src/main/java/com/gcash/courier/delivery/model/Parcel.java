package com.gcash.courier.delivery.model;


public record Parcel(

        float weight,
        float height,
        float width,
        float length,
        String voucherCode
) {
}
