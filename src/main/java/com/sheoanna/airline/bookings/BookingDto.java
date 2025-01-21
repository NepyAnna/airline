package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;
import java.util.List;

import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.users.UserDto;

public record BookingDto(Long idBooking, UserDto user, List<Flight> flights, LocalDateTime dateBooking, int bookedSeats) {}
