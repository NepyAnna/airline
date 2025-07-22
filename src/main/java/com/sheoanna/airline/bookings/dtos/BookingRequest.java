package com.sheoanna.airline.bookings.dtos;

import java.time.LocalDateTime;

public record BookingRequest(String departureCodeIata,
                             String arrivalCodeIata,
                             LocalDateTime dateFlight,
                             int bookedSeats) {}
