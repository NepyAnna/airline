package com.sheoanna.airline.airport.exceptions;

public class AirportException extends RuntimeException {
    public AirportException(String message) {
        super(message);
    }

    public AirportException(String message, Throwable cause) {
        super(message, cause);
    }
}
