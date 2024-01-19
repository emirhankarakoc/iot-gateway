package com.aselsis.iot.gateway.exceptions;

import com.aselsis.iot.gateway.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public BadRequestException(String message){
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
