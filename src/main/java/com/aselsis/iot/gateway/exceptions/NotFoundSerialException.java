package com.aselsis.iot.gateway.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundSerialException extends RestException{
    @Getter
    private final HttpStatus httpStatus;

    public NotFoundSerialException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
