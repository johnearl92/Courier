package com.gcash.courier.delivery.model;

import java.time.LocalDate;

public record VoucherResponse(
        String code,
        float discount,
        LocalDate expiry
) {
}
