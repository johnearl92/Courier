package com.gcash.courier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParcelException extends RuntimeException {
    public ParcelException(String message) {
        super(message);
    }
}
