package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.FlightStatus;

import java.time.LocalDateTime;

public record FlightResponse(
        Long id,
        String departureAirportCode,
        String arrivalAirportCode,
        LocalDateTime dateFlight,
        FlightStatus status,
        float price,
        int availableSeats,
        int totalSeats
) {}
