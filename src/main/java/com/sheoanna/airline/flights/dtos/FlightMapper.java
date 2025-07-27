package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    public Flight toEntity(FlightRequest request) {

        return Flight.builder()
                .dateFlight(request.dateFlight())
                .price(request.price())
                .status(request.status())
                .availableSeats(request.availableSeats())
                .totalSeats(request.totalSeats())
                .build();
    }

    public FlightResponse toResponse(Flight flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getDepartureAirport().getCodeIata(),
                flight.getArrivalAirport().getCodeIata(),
                flight.getDateFlight(),
                flight.getPrice(),
                flight.getStatus(),
                flight.getAvailableSeats(),
                flight.getTotalSeats()
        );
    }
}