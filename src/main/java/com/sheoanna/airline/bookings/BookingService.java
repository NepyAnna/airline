package com.sheoanna.airline.bookings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.bookings.exceptions.BookingException;
import com.sheoanna.airline.bookings.exceptions.BookingNotFoundException;
import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.flights.FlightRepository;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;
import com.sheoanna.airline.users.UserNameDto;
import com.sheoanna.airline.users.UserRepository;
import com.sheoanna.airline.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        List<Booking> bookings = repository.findAll();
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
        booking.setUser(userRepository.findByUsername(bookingDto.user().username()).orElseThrow(() -> new UserNotFoundException("User not found with username: " + bookingDto.user().username())));
                
        booking.setFlight(flight);
        booking.setDateBooking(bookingDto.dateBooking());
        booking.setBookedSeats(bookingDto.bookedSeats());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = repository.save(booking);

        scheduleSeatRelease(savedBooking);// !!!!!!!!!!!!!!!!!!CHECK!!!!!!!!!!!!!!!!!!!

        return bookingToBookingDto(savedBooking);
    }

    @Transactional
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {
        Booking existingBooking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));


                User user = userRepository.findByUsername(bookingDto.user().username())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + bookingDto.user().username()));

               Flight flight = flightRepository.findFlightByParameters(
                bookingDto.flight().departureAirport().idAirport(),
                bookingDto.flight().arrivalAirport().idAirport(),
                bookingDto.flight().dateFlight(),
                bookingDto.bookedSeats())
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with the specified parameters"));

        int newSeatsDelta = bookingDto.bookedSeats() - existingBooking.getBookedSeats();
        if (flight.getAvailableSeats() < newSeatsDelta) {
            throw new BookingException("Not enough available seats on this flight");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - newSeatsDelta);
        flightRepository.save(flight);

        existingBooking.setUser(user);
        existingBooking.setFlight(flight);
        existingBooking.setDateBooking(bookingDto.dateBooking());
        existingBooking.setBookedSeats(bookingDto.bookedSeats());

        Booking updatedBooking = repository.save(existingBooking);

        return bookingToBookingDto(updatedBooking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getBookedSeats());
        flightRepository.save(flight);
        repository.delete(booking);
    }

    ////////////////// CHEcCK???????????????????????????????????????

    private void scheduleSeatRelease(Booking booking) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            Booking existingBooking = repository.findById(booking.getIdBooking()).orElse(null);
            if (existingBooking != null && !isBookingConfirmed(existingBooking)) {
                Flight flight = existingBooking.getFlight();
                flight.setAvailableSeats(flight.getAvailableSeats() + existingBooking.getBookedSeats());
                flightRepository.save(flight);
                repository.deleteById(existingBooking.getIdBooking());
            }
        }, 15, TimeUnit.MINUTES);
    }

    private boolean isBookingConfirmed(Booking booking) {

        return booking.getStatus() == BookingStatus.CONFIRMED;
    }

    private UserNameDto toUserDto(User user) {
        return new UserNameDto(user.getUsername());
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
