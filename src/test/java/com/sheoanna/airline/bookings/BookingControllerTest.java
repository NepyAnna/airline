package com.sheoanna.airline.bookings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.sheoanna.airline.airport.AirportDto;

import com.sheoanna.airline.flights.FlightDto;
import com.sheoanna.airline.flights.FlightStatus;
import com.sheoanna.airline.users.UserNameDto;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void testIndex() {
        AirportDto departureAirport = new AirportDto(1L, "Departure Airport", "DPT");
        AirportDto arrivalAirport = new AirportDto(2L, "Arrival Airport", "ARR");
        FlightDto flight = new FlightDto(1L, departureAirport, arrivalAirport, LocalDateTime.now(),
                FlightStatus.AVAILABLE, 200.0f, 100, 150);
        BookingDto booking1 = new BookingDto(1L, new UserNameDto("john"), flight, LocalDateTime.now(), 2);
        BookingDto booking2 = new BookingDto(2L, new UserNameDto("jane"), flight, LocalDateTime.now(), 3);

        List<BookingDto> bookings = List.of(booking1, booking2);

        when(bookingService.getAll()).thenReturn(bookings);
        List<BookingDto> response = bookingController.index();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).idBooking());
        assertEquals("john", response.get(0).user().username());

        verify(bookingService).getAll();
    }

    @Test
    void testCreateBooking() {
        AirportDto departureAirport = new AirportDto(1L, "Departure Airport", "DPT");
        AirportDto arrivalAirport = new AirportDto(2L, "Arrival Airport", "ARR");
        FlightDto flight = new FlightDto(1L, departureAirport, arrivalAirport, LocalDateTime.now(),
                FlightStatus.AVAILABLE, 200.0f, 100, 150);
        BookingDto newBookingDto = new BookingDto(1L, new UserNameDto("john"), flight, LocalDateTime.now(), 2);
        BookingDto savedBookingDto = new BookingDto(1L, new UserNameDto("john"), flight, LocalDateTime.now(), 2);

        when(bookingService.createBooking(any(BookingDto.class))).thenReturn(savedBookingDto);

        ResponseEntity<BookingDto> response = bookingController.createBooking(newBookingDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(bookingService).createBooking(any(BookingDto.class));
    }

    @Test
    void testUpdateBooking() {
        AirportDto departureAirport = new AirportDto(1L, "Departure Airport", "DPT");
        AirportDto arrivalAirport = new AirportDto(2L, "Arrival Airport", "ARR");
        FlightDto flight = new FlightDto(1L, departureAirport, arrivalAirport, LocalDateTime.now(),
                FlightStatus.AVAILABLE, 200.0f, 100, 150);

        Long bookingId = 1L;
        BookingDto updatedBookingDto = new BookingDto(1L, new UserNameDto("john"), flight, LocalDateTime.now(), 2);
        BookingDto updatedBooking = new BookingDto(1L, new UserNameDto("john"), flight, LocalDateTime.now(), 2);

        when(bookingService.updateBooking(bookingId, updatedBookingDto)).thenReturn(updatedBooking);

        ResponseEntity<BookingDto> response = bookingController.putBookingById(bookingId, updatedBookingDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(bookingService).updateBooking(bookingId, updatedBookingDto);
    }

    @Test
    void testDeleteBooking() {
        Long bookingId = 1L;

        doNothing().when(bookingService).deleteBooking(bookingId);

        ResponseEntity<Void> response = bookingController.deleteBookingById(bookingId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookingService).deleteBooking(bookingId);
    }
}
