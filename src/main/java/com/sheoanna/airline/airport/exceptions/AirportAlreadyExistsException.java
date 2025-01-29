package com.sheoanna.airline.airport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Airport alredy exist")
public class AirportAlreadyExistsException extends AirportException {
    public AirportAlreadyExistsException(String message) {
        super(message);
    }

    public AirportAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
