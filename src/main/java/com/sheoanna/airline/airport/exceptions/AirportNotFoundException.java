package com.sheoanna.airline.airport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Airport not found")
public class AirportNotFoundException extends AirportException{
    
     public AirportNotFoundException(String message) {
        super(message);
    }

    public AirportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
