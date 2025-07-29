package com.sheoanna.airline.flights;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportService;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;
import com.sheoanna.airline.flights.dtos.FlightMapper;
import com.sheoanna.airline.flights.dtos.FlightRequest;
import com.sheoanna.airline.flights.dtos.FlightResponse;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirportService airportService;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightService flightService;

    private Airport departureAirport;
    private Airport arrivalAirport;
    private FlightRequest flightRequest;
    private Flight flight;
    private FlightResponse flightResponse;

    @BeforeEach
    void setUp() {
        departureAirport = Airport.builder().id(1L).name("Boryspil").codeIata("KBP").build();
        arrivalAirport = Airport.builder().id(2L).name("Heathrow").codeIata("LHR").build();

        flightRequest = new FlightRequest(
                "KBP", "LHR",
                LocalDateTime.of(2025, 8, 1, 10, 0),
                300.0, FlightStatus.AVAILABLE, 100, 150
        );

        flight = Flight.builder()
                .id(1L)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .dateFlight(flightRequest.dateFlight())
                .price(flightRequest.price())
                .status(FlightStatus.AVAILABLE)
                .availableSeats(100)
                .totalSeats(150)
                .build();

        flightResponse = new FlightResponse(
                1L, "KBP", "LHR", flight.getDateFlight(), 300.0,
                FlightStatus.AVAILABLE, 100, 150
        );
    }

    @Test
    void storeFlight_shouldSaveAndReturnFlightResponse() {
        when(airportService.findObjByCodeIata("KBP")).thenReturn(departureAirport);
        when(airportService.findObjByCodeIata("LHR")).thenReturn(arrivalAirport);
        when(flightMapper.toEntity(flightRequest)).thenReturn(flight);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(flightMapper.toResponse(flight)).thenReturn(flightResponse);

        FlightResponse result = flightService.storeFlight(flightRequest);

        assertEquals("KBP", result.departureAirportCode());
        assertEquals("LHR", result.arrivalAirportCode());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void storeFlight_shouldThrow_whenDepartureAirportNotFound() {
        when(airportService.findObjByCodeIata("KBP"))
                .thenThrow(new AirportNotFoundException("KBP"));

        assertThrows(AirportNotFoundException.class, () -> flightService.storeFlight(flightRequest));
    }

    @Test
    void findFlightById_shouldReturnFlightResponse() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightMapper.toResponse(flight)).thenReturn(flightResponse);

        FlightResponse result = flightService.findFlightById(1L);

        assertEquals("KBP", result.departureAirportCode());
        verify(flightRepository).findById(1L);
    }

    @Test
    void findFlightById_shouldThrow_whenNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightService.findFlightById(1L));
    }

    @Test
    void updateFlight_shouldUpdateAndReturnResponse() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(airportService.findObjByCodeIata("KBP")).thenReturn(departureAirport);
        when(airportService.findObjByCodeIata("LHR")).thenReturn(arrivalAirport);
        when(flightMapper.toResponse(flight)).thenReturn(flightResponse);

        FlightResponse updated = flightService.updateFlight(1L, flightRequest);

        assertEquals("KBP", updated.departureAirportCode());
        verify(flightRepository).findById(1L);
    }

    @Test
    void deleteFlightById_shouldDeleteIfExists() {
        when(flightRepository.existsById(1L)).thenReturn(true);

        flightService.deleteFlightById(1L);

        verify(flightRepository).deleteById(1L);
    }

    @Test
    void deleteFlightById_shouldThrowIfNotExists() {
        when(flightRepository.existsById(1L)).thenReturn(false);

        assertThrows(FlightNotFoundException.class, () -> flightService.deleteFlightById(1L));
    }

    @Test
    void findFlightByParameters_shouldReturnFlight() {
        when(flightRepository.findFlightByParameters("KBP", "LHR", flight.getDateFlight(), 1))
                .thenReturn(Optional.of(flight));

        Flight result = flightService.findFlightByParameters("KBP", "LHR", flight.getDateFlight(), 1);

        assertEquals("KBP", result.getDepartureAirport().getCodeIata());
    }

    @Test
    void findFlightByParameters_shouldThrowWhenNotFound() {
        when(flightRepository.findFlightByParameters("KBP", "LHR", flight.getDateFlight(), 1))
                .thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class,
                () -> flightService.findFlightByParameters("KBP", "LHR", flight.getDateFlight(), 1));
    }
}