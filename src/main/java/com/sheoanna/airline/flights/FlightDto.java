package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import com.sheoanna.airline.airport.AirportDto;

public record FlightDto(Long idFlight, AirportDto departureAirport, AirportDto arrivalAirport, LocalDateTime dateFlight, FlightStatus statusFlight, float price, int availableSeats, int totalSeats) {}