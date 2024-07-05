package com.gcash.courier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ParcelException extends RuntimeException {
    public ParcelException(String message) {
        super(message);
    }
}
