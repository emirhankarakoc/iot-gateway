package com.aselsis.iot.gateway.exceptions;

import com.aselsis.iot.gateway.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public EntityNotFoundException(String message){
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
