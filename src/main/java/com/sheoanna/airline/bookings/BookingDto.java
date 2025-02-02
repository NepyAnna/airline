package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.users.UserNameDto;

public record BookingDto(Long idBooking, UserNameDto user, FlightDto flight, LocalDateTime dateBooking,
                int bookedSeats) {
}
