package com.sheoanna.airline.bookings.dtos;

import com.sheoanna.airline.bookings.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getUsername(),
                booking.getFlight().getDepartureAirport().getCodeIata(),
                booking.getFlight().getArrivalAirport().getCodeIata(),
                booking.getFlight().getDateFlight(),
                booking.getDateBooking(),
                booking.getBookedSeats(),
                booking.getStatus()
        );
    }

    public Booking toEntity(BookingRequest request) {
        return Booking.builder()
                .bookedSeats(request.bookedSeats())
                .build();
    }
}
