package com.sheoanna.airline.bookings.dtos;
import com.sheoanna.airline.bookings.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.html.parser.Entity;

@Mapper(componentModel = "spring")
public interface BookingMapper {
/*    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "flight.departureAirport.codeIata", target = "flightCode")
    BookingResponse toResponse(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Booking toEntity(BookingRequest request);*/
}
