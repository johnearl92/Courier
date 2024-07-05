package com.gcash.courier.delivery.model;


public record Parcel(

        Double weight,
        Double height,
        Double width,
        Double length,
        String voucherCode
) {
}
