package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.FlightStatus;

import java.time.LocalDateTime;

public record FlightRequest(
        String departureAirportIata,
        String arrivalAirportIata,
        LocalDateTime dateFlight,
        double price,
        FlightStatus status,
        int availableSeats,
        int totalSeats
) {}
