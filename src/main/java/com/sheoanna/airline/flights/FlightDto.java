package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import com.sheoanna.airline.airport.Airport;

public record FlightDto(Long idFlight, Airport departureAirport, Airport arrivalAirport, LocalDateTime flightDate,
            FlightStatus status, int availableSeats, int totalSeats) {}
