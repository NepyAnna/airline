package com.sheoanna.airline.bookings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.flights.FlightNotFoundException;
import com.sheoanna.airline.flights.FlightRepository;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;
import com.sheoanna.airline.users.UserRepository;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository repository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository repository, FlightRepository flightRepository,
            UserRepository userRepository) {
        this.repository = repository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        Flight flight = flightRepository.findFlightByParameters(
                bookingDto.flight().departureAirport().idAirport(),
                bookingDto.flight().arrivalAirport().idAirport(),
                bookingDto.flight().dateFlight(),
                bookingDto.bookedSeats())
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with the specified parameters"));

        if (flight.getAvailableSeats() < bookingDto.bookedSeats()) {
            throw new BookingException("Not enough available seats on this flight");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - bookingDto.bookedSeats());
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setUser(userRepository.findById(bookingDto.user().idUser())
                .orElseThrow(() -> new RuntimeException("User not found")));
        booking.setFlight(flight);
        booking.setDateBooking(bookingDto.dateBooking());
        booking.setBookedSeats(bookingDto.bookedSeats());

        Booking savedBooking = repository.save(booking);

        return bookingToBookingDto(savedBooking);
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

    private BookingDto bookingToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getIdBooking(),
                toUserDto(booking.getUser()),
                flightToFlightDto(booking.getFlight()),
                booking.getDateBooking(),
                booking.getBookedSeats());
    }
}
