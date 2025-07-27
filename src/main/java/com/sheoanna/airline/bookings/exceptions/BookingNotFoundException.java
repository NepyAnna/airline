package com.sheoanna.airline.bookings.exceptions;

import com.sheoanna.airline.global.AppException;

public class BookingNotFoundException extends AppException {
    public BookingNotFoundException(Long id) {
        super("Booking with ID " + id + " not found ");
    }
    public BookingNotFoundException(String message) {
        super(message);
    }
    public BookingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
