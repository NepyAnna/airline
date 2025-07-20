package com.sheoanna.airline.flights.dtos;

import java.time.LocalDateTime;

public record FlightRequest(
        Long departureAirportId,
        Long arrivalAirportId,
        LocalDateTime dateFlight,
        float price,
        int totalSeats
) {}
