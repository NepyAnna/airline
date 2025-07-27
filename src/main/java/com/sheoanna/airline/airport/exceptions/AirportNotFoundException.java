package com.sheoanna.airline.airport.exceptions;

import com.sheoanna.airline.global.AppException;

public class AirportNotFoundException extends AppException {
    public AirportNotFoundException(Long id) {
        super("Airport with ID " + id + " not found");
    }
    public AirportNotFoundException(String iata) {
        super("Airport with IATA code  " + iata + " not found");
    }
    public AirportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
