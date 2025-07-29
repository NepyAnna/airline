package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.FlightStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record FlightRequest(@NotBlank(message = "Code IATA of departure airport required")
                            @Pattern(regexp = "^[A-Z]{3}$", message = "Departure IATA code must be 3 uppercase letters")
                            String departureAirportIata,
                            @NotBlank(message = "Code IATA of arrival airport required")
                            @Pattern(regexp = "^[A-Z]{3}$", message = "Departure IATA code must be 3 uppercase letters")
                            String arrivalAirportIata,
                            @FutureOrPresent(message = "Flight date must not be in the past")
                            @NotNull(message = "Flight date is required")
                            LocalDateTime dateFlight,
                            @PositiveOrZero(message = "Price must be zero or positive")
                            double price,
                            @NotNull(message = "Flight status is required")
                            FlightStatus status,
                            @Min(value = 0, message = "Available seats must be zero or positive")
                            int availableSeats,
                            @Min(value = 1, message = "Total seats must be at least 1")
                            int totalSeats
) {}
