package com.sheoanna.airline.bookings.dtos;

public record BookingRequest(Long userId,
                             Long flightId,
                             int bookedSeats) {}
