package com.sheoanna.airline.airport.exceptions;

import com.sheoanna.airline.global.AppException;

public class AirportAlreadyExistsException extends AppException {
    public AirportAlreadyExistsException(Long id) {
        super("Airport with ID " + id + " already exists.");}
    public AirportAlreadyExistsException(String iata) {
        super("Airport with IATA code " + iata + " already exists.");
    }
    public AirportAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
