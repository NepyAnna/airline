package com.sheoanna.airline.flights.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Flight not found")
public class FlightNotFoundException extends FlightException{
    public FlightNotFoundException(String message) {
        super(message);
    }

    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
