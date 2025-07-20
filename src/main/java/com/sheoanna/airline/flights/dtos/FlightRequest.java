package com.sheoanna.airline.flights.dtos;

import java.time.LocalDateTime;

public record FlightRequest(
        String departureAirportIata,
        String arrivalAirportIata,
        LocalDateTime dateFlight,
        float price,
        int totalSeats
) {}
