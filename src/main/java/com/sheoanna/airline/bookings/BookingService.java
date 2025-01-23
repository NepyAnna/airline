package com.sheoanna.airline.bookings;

import org.springframework.stereotype.Service;

import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public List<BookingDto> getAll() {
        List<Booking> bookings = repository.findAllWithDetails();// repository.findAll();
        return bookings.stream().map(booking -> new BookingDto(booking.getIdBooking(),
                toUserDto(booking.getUser()),
                flightToFlightDto(booking.getFlight()),
                booking.getDateBooking(),
                booking.getBookedSeats())).toList();
    }

    public BookingDto getById(Long id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

        BookingDto bookingDto = new BookingDto(
                booking.getIdBooking(),
                toUserDto(booking.getUser()),
                flightToFlightDto(booking.getFlight()),
                booking.getDateBooking(),
                booking.getBookedSeats());

        return bookingDto;
    }

    private UserDto toUserDto(User user) {

        return new UserDto(user.getIdUser(), user.getUsername());
    }

    private FlightDto flightToFlightDto(Flight flight) {
        FlightDto flightDto = new FlightDto(flight.getIdFlight(),
                airportToAirportDto(flight.getDepartureAirport()),
                airportToAirportDto(flight.getArrivalAirport()),
                flight.getDateFlight(), flight.getStatusFlight(),
                flight.getPrice(),
                flight.getAvailableSeats(),
                flight.getTotalSeats());
        return flightDto;
    }

    private AirportDto airportToAirportDto(Airport airport) {
        return new AirportDto(
                airport.getIdAirport(),
                airport.getNameAirport(),
                airport.getCodeIata());
    }
}
