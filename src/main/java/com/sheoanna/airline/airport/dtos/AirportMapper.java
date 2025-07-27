package com.sheoanna.airline.airport.dtos;

import com.sheoanna.airline.airport.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportResponse toResponse(Airport airport);
    Airport toEntity(AirportRequest request);
}
