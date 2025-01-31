package com.sheoanna.airline.bookings;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.flights.FlightRepository;
import com.sheoanna.airline.flights.FlightStatus;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserNameDto;
import com.sheoanna.airline.users.UserRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

        @Mock
        private BookingRepository bookingRepository;

        @Mock
        private FlightRepository flightRepository;

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private BookingService bookingService;

        @Test
        void testCreateBooking() {
                AirportDto departureAirportDto = new AirportDto(1L, "Departure Airport", "DPT");
                AirportDto arrivalAirportDto = new AirportDto(2L, "Arrival Airport", "ARR");

                Airport departureAirport = new Airport(1L, "Departure Airport", "DPT");
                Airport arrivalAirport = new Airport(2L, "Arrival Airport", "ARR");

                FlightDto flightDto = new FlightDto(1L, departureAirportDto, arrivalAirportDto, LocalDateTime.now(),
                                FlightStatus.AVAILABLE, 200.0f, 100, 150);
                BookingDto bookingDto = new BookingDto(1L, new UserNameDto("john"), flightDto, LocalDateTime.now(), 2);

                Flight flight = new Flight(departureAirport, arrivalAirport, LocalDateTime.now(),
                                FlightStatus.AVAILABLE,
                                200.0f, 100, 150);

                User user = new User(1L, "john");

                when(flightRepository.findFlightByParameters(anyLong(), anyLong(), any(), anyInt()))
                                .thenReturn(Optional.of(flight));
                when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(new Booking(user, LocalDateTime.now(), 2, flight));

                BookingDto result = bookingService.createBooking(bookingDto);

                assertNotNull(result);
                verify(bookingRepository).save(any(Booking.class));
                verify(flightRepository).save(flight);
        }

        @Test
        void testUpdateBooking() {
                AirportDto departureAirportDto = new AirportDto(1L, "Departure Airport", "DPT");
                AirportDto arrivalAirportDto = new AirportDto(2L, "Arrival Airport", "ARR");
                Airport departureAirport = new Airport(1L, "Departure Airport", "DPT");
                Airport arrivalAirport = new Airport(2L, "Arrival Airport", "ARR");

                Flight flight = new Flight(departureAirport, arrivalAirport, LocalDateTime.now(),
                                FlightStatus.AVAILABLE,
                                200.0f, 100, 150);
                FlightDto flightDto = new FlightDto(1L, departureAirportDto, arrivalAirportDto, LocalDateTime.now(),
                                FlightStatus.AVAILABLE, 200.0f, 100, 150);

                BookingDto bookingDto = new BookingDto(1L, new UserNameDto("john"), flightDto, LocalDateTime.now(), 2);
                User user = new User(1L, "john");
                Booking booking = new Booking(user, LocalDateTime.now(), 2, flight);

                Long bookingId = 1L;
                Booking existingBooking = booking;
                BookingDto updatedBookingDto = bookingDto;

                when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(
                                existingBooking));
                when(userRepository.findByUsername(updatedBookingDto.user().username())).thenReturn(Optional.of(user));
                when(flightRepository.findFlightByParameters(anyLong(), anyLong(), any(),
                                anyInt()))
                                .thenReturn(Optional.of(flight));
                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(new Booking(user, LocalDateTime.now(), 2, flight));

                BookingDto result = bookingService.updateBooking(bookingId,
                                updatedBookingDto);

                assertNotNull(result);
                verify(bookingRepository).save(any(Booking.class));
                verify(flightRepository).save(flight);
        }

        @Test
        void testDeleteBooking() {
                Long bookingId = 1L;
                Airport departureAirport = new Airport(1L, "Departure Airport", "DPT");
                Airport arrivalAirport = new Airport(2L, "Arrival Airport", "ARR");

                Flight flight = new Flight(departureAirport, arrivalAirport, LocalDateTime.now(),
                                FlightStatus.AVAILABLE,
                                200.0f, 100, 150);
                User user = new User(1L, "john");
                Booking booking = new Booking(user, LocalDateTime.now(), 2, flight);

                when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

                bookingService.deleteBooking(bookingId);

                verify(bookingRepository).delete(booking);
                verify(flightRepository).save(flight);
        }
}
