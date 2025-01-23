package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;

import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.users.UserDto;

public record BookingDto(Long idBooking, UserDto user, Flight flight, LocalDateTime dateBooking, int bookedSeats) {}
