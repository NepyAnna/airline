package com.sheoanna.airline.bookings.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record BookingRequest(@NotBlank(message="Code IATA of departure airport required")
                             @Pattern(regexp = "^[A-Z]{3}$", message = "Departure IATA code must be 3 uppercase letters")
                             String departureCodeIata,
                             @NotBlank(message="Code IATA of arrival airport required")
                             @Pattern(regexp = "^[A-Z]{3}$", message = "Departure IATA code must be 3 uppercase letters")
                             String arrivalCodeIata,
                             @FutureOrPresent(message = "Flight date must not be in the past")
                             @NotNull(message = "Flight date is required")
                             LocalDateTime dateFlight,
                             int bookedSeats) {}
