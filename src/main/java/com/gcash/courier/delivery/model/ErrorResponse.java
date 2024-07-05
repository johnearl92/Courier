package com.gcash.courier.delivery.model;


import java.util.List;

public record ErrorResponse(
    String title,
    Integer status,
    List<String> errors
) {
}
