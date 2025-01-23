package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.users.UserDto;

public record BookingDto(Long idBooking, UserDto user, FlightDto flight, LocalDateTime dateBooking, int bookedSeats) {}
