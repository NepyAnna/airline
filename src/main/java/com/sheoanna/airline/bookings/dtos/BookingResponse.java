package com.sheoanna.airline.bookings.dtos;

import com.sheoanna.airline.bookings.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        String username,
        String flightCode,
        LocalDateTime dateBooking,
        int bookedSeats,
        BookingStatus status
) {}