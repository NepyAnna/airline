package com.sheoanna.airline.flights.exceptions;

import com.sheoanna.airline.global.AppException;

public class FlightNotFoundException extends AppException {
    public FlightNotFoundException(Long id) {super("Flight with ID " + id + " not found.");}
    public FlightNotFoundException(String message) {
        super(message);
    }
    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
