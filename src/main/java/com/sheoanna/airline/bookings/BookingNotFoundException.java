package com.sheoanna.airline.bookings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;




@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Booking not found")
public class BookingNotFoundException extends BookingException{
    
     public BookingNotFoundException(String message) {
        super(message);
    }

    public BookingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

