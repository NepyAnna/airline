package com.sheoanna.airline.bookings;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.bookings.dtos.BookingMapper;
import com.sheoanna.airline.bookings.dtos.BookingRequest;
import com.sheoanna.airline.bookings.dtos.BookingResponse;
import com.sheoanna.airline.bookings.exceptions.BookingNotFoundException;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightService;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightService flightService;

    @Mock
    private UserService userService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    private User testUser;
    private Flight testFlight;
    private Booking testBooking;
    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).username("testUser").build();

        Airport departureAirport = Airport.builder().codeIata("JFK").build();
        Airport arrivalAirport = Airport.builder().codeIata("LAX").build();

        testFlight = Flight.builder()
                .id(1L)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .availableSeats(10)
                .build();

        bookingRequest = new BookingRequest("JFK", "LAX", LocalDateTime.now().plusDays(1), 2);

        testBooking = Booking.builder()
                .id(1L)
                .user(testUser)
                .flight(testFlight)
                .bookedSeats(2)
                .status(BookingStatus.CONFIRMED)
                .dateBooking(LocalDateTime.now())
                .build();

        bookingResponse = new BookingResponse(1L, "testUser", "JFK", "LAX",
                testFlight.getDateFlight(), testBooking.getDateBooking(), 2, BookingStatus.CONFIRMED);
    }

    @Test
    void createBooking_AdminNotAllowed() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(userService.isAdmin(testUser)).thenReturn(true);

        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () -> {
            bookingService.createBooking(bookingRequest);
        });
        assertEquals("ADMIN is not allowed to add a booking.", ex.getMessage());
    }

    @Test
    void updateBooking_NotOwnerAccessDenied() {
        Booking existingBooking = testBooking;
        User otherUser = User.builder().id(2L).username("otherUser").build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(existingBooking));
        when(userService.getAuthenticatedUser()).thenReturn(otherUser);

        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () -> {
            bookingService.updateBooking(1L, bookingRequest);
        });
        assertEquals("You are not allowed  to change this booking.", ex.getMessage());
    }

    @Test
    void deleteBooking_Success() {
        Booking existingBooking = testBooking;
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(existingBooking));
        when(userService.getAuthenticatedUser()).thenReturn(testUser);

        bookingService.deleteBooking(1L);

        verify(bookingRepository).delete(existingBooking);
        assertEquals(testFlight.getAvailableSeats() + existingBooking.getBookedSeats(), testFlight.getAvailableSeats() + 2);
    }

    @Test
    void deleteBooking_NotOwnerAccessDenied() {
        Booking existingBooking = testBooking;
        User otherUser = User.builder().id(2L).username("otherUser").build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(existingBooking));
        when(userService.getAuthenticatedUser()).thenReturn(otherUser);

        AccessDeniedException ex = assertThrows(AccessDeniedException.class, () -> {
            bookingService.deleteBooking(1L);
        });
        assertEquals("You are not allowed  to delete this booking.", ex.getMessage());
    }

    @Test
    void findBookingById_Success() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingMapper.toResponse(testBooking)).thenReturn(bookingResponse);

        BookingResponse response = bookingService.findBookingById(1L);

        assertNotNull(response);
        assertEquals("testUser", response.username());
    }

    @Test
    void findBookingById_NotFound() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.findBookingById(1L));
    }
}