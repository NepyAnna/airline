package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.Set;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.bookings.Booking;

public record FlightDto(Long id, Airport departureAirport, Airport arrivalAirport, Airport airport, LocalDateTime flightDate,
            FlightStatus status, int availableSeats, Set<Booking> bookings) {

}
