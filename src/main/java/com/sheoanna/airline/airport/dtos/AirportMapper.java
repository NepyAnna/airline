package com.sheoanna.airline.airport.dtos;

import com.sheoanna.airline.airport.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    @Mapping(source = "name", target = "name")
    AirportResponse toResponse(Airport airport);

    @Mapping(source = "name", target = "name")
    Airport toEntity(AirportRequest request);
}
