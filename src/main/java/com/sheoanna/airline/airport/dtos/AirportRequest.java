package com.sheoanna.airline.airport.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AirportRequest(@NotBlank(message="Airport name is required.")
                             String name,
                             @NotBlank(message="Code IATA of departure airport required")
                             @Pattern(regexp = "^[A-Z]{3}$", message = "Departure IATA code must be 3 uppercase letters")
                             String codeIata) {}
