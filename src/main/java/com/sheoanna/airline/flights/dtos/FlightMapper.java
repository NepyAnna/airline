package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {
/*    @Mapping(source = "departureAirport.codeIata", target = "departureAirportIata")
    @Mapping(source = "arrivalAirport.codeIata", target = "arrivalAirportIata")
    FlightResponse toResponse(Flight flight);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "AVAILABLE")
    @Mapping(target = "availableSeats", source = "totalSeats")
    @Mapping(target = "bookings", ignore = true)
    Flight toEntity(FlightRequest request);*/
}