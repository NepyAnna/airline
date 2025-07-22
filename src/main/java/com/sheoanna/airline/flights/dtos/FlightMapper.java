package com.sheoanna.airline.flights.dtos;

import com.sheoanna.airline.flights.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    @Mapping(target = "departureAirportCode", source = "departureAirport.codeIata")
    @Mapping(target = "arrivalAirportCode", source = "arrivalAirport.codeIata")
    FlightResponse toResponse(Flight flight);

    @Mapping(target = "departureAirport", ignore = true)
    @Mapping(target = "arrivalAirport", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    Flight toEntity(FlightRequest request);
}