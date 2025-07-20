package com.sheoanna.airline.airport.dtos;

import com.sheoanna.airline.airport.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportMapper INSTANCE = Mappers.getMapper(AirportMapper.class);
    AirportResponse toResponse(Airport airport);
    Airport toEntity(AirportRequest request);
}
