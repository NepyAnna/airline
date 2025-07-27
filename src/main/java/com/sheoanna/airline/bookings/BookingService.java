package com.sheoanna.airline.bookings;

import com.sheoanna.airline.bookings.dtos.BookingMapper;
import com.sheoanna.airline.bookings.dtos.BookingRequest;
import com.sheoanna.airline.bookings.dtos.BookingResponse;
import com.sheoanna.airline.flights.FlightService;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.sheoanna.airline.bookings.exceptions.BookingNotFoundException;
import com.sheoanna.airline.flights.Flight;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    public Page<BookingResponse> findAllBookings(Pageable pageble) {
        return bookingRepository.findAll(pageble)
                .map(bookingMapper::toResponse);
    }

    public BookingResponse findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        return bookingMapper.toResponse(booking);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BookingResponse createBooking(BookingRequest newBooking) {
        User user = userService.getAuthenticatedUser();

        if (userService.isAdmin(user)) {
            throw new AccessDeniedException("ADMIN is not allowed to add a booking.");
        }
        Flight flight = flightService.findFlightByParameters(
                newBooking.departureCodeIata(),
                newBooking.arrivalCodeIata(),
                newBooking.dateFlight(),
                newBooking.bookedSeats());

        if (flight.getAvailableSeats() < newBooking.bookedSeats()) {
            throw new FlightNotFoundException("Not enough available seats on this flight");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - newBooking.bookedSeats());

        Booking booking = bookingMapper.toEntity(newBooking);
        booking.setFlight(flight);
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setDateBooking(LocalDateTime.now());
        booking.setBookedSeats(newBooking.bookedSeats());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);
        scheduleSeatRelease(savedBooking);
        return bookingMapper.toResponse(savedBooking);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookingResponse updateBooking(Long id, BookingRequest updateBookingData) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        User user = userService.getAuthenticatedUser();

        if (!user.getUsername().equals(existingBooking.getUser().getUsername())) {
            throw new AccessDeniedException("You are not allowed  to change this booking.");
        }

        Flight flight = flightService.findFlightByParameters(
                updateBookingData.departureCodeIata(),
                updateBookingData.arrivalCodeIata(),
                updateBookingData.dateFlight(),
                updateBookingData.bookedSeats());

        int newSeatsDelta = updateBookingData.bookedSeats() - existingBooking.getBookedSeats();
        if (flight.getAvailableSeats() < newSeatsDelta) {
            throw new FlightNotFoundException("Not enough available seats on this flight");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - newSeatsDelta);

        existingBooking.setUser(user);
        existingBooking.setFlight(flight);
        existingBooking.setBookedSeats(updateBookingData.bookedSeats());

        return bookingMapper.toResponse(existingBooking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        User user = userService.getAuthenticatedUser();

        if (!user.getUsername().equals(existingBooking.getUser().getUsername())) {
            throw new AccessDeniedException("You are not allowed  to delete this booking.");
        }
        Flight flight = existingBooking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + existingBooking.getBookedSeats());
        existingBooking.setFlight(null);
        bookingRepository.delete(existingBooking);
    }

    private void scheduleSeatRelease(Booking booking) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            Booking existingBooking = bookingRepository.findById(booking.getId()).orElse(null);
            if (existingBooking != null && !isBookingConfirmed(existingBooking)) {
                Flight flight = existingBooking.getFlight();
                flight.setAvailableSeats(flight.getAvailableSeats() + existingBooking.getBookedSeats());
                bookingRepository.deleteById(existingBooking.getId());
            }
        }, 15, TimeUnit.MINUTES);
    }

    private boolean isBookingConfirmed(Booking booking) {
        return booking.getStatus() == BookingStatus.CONFIRMED;
    }
}
