package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.Flight;
import org.springframework.stereotype.Component;


@Component
//@RequiredArgsConstructor
public class FlightMapper {

    //private final AirportRepository airportRepository;

    public Flight toEntity(FlightRequest request) {
/*        Airport departureAirport = airportRepository.findByCodeIata(request.departureAirportIata())
                .orElseThrow(() -> new IllegalArgumentException("Departure airport not found: " + request.departureAirportIata()));
        Airport arrivalAirport = airportRepository.findByCodeIata(request.arrivalAirportIata())
                .orElseThrow(() -> new IllegalArgumentException("Arrival airport not found: " + request.arrivalAirportIata()));*/

        return Flight.builder()
                //.departureAirport(departureAirport)
                //.arrivalAirport(arrivalAirport)
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