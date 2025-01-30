package com.sheoanna.airline.flights;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportRepository;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private FlightService flightService;

    private Airport departureAirport;
    private Airport arrivalAirport;
    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departureAirport = new Airport(1L, "Departure Airport", "DPT");
        arrivalAirport = new Airport(2L, "Arrival Airport", "ARR");
        flight = new Flight(departureAirport, arrivalAirport, LocalDateTime.now(), FlightStatus.AVAILABLE, 200.0f, 100, 150);
    }

    @Test
    void testGetAllFlights() {
        when(flightRepository.findAll()).thenReturn(Collections.singletonList(flight));

        List<FlightDto> flights = flightService.getAll();

        assertNotNull(flights);
        assertEquals(1, flights.size());
        assertEquals(flight.getIdFlight(), flights.get(0).idFlight());
    }

    @Test
    void testGetById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightDto flightDto = flightService.getById(1L);

        assertNotNull(flightDto);
        assertEquals(flight.getIdFlight(), flightDto.idFlight());
    }

   /*  @Test
    void testStoreFlight() {
        when(airportRepository.findById(1L)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(arrivalAirport));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        FlightDto newFlightDto = new FlightDto(1L, new AirportDto(1L, "Departure", "DPT"), new AirportDto(2L, "Arrival", "ARR"), LocalDateTime.now(), FlightStatus.AVAILABLE, 200.0f, 100, 150);
        FlightDto createdFlightDto = flightService.store(newFlightDto);

        assertNotNull(createdFlightDto);
        assertEquals(newFlightDto.idFlight(), createdFlightDto.idFlight());
    }

    @Test
    void testUpdateFlight() {
        Flight updatedFlight = new Flight(departureAirport, arrivalAirport, LocalDateTime.now(), FlightStatus.UNAVAILABLE, 180.0f, 80, 120);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(airportRepository.findById(1L)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(arrivalAirport));
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);

        FlightDto updatedFlightDto = new FlightDto(1L, new AirportDto(1L, "Departure", "DPT"), new AirportDto(2L, "Arrival", "ARR"), LocalDateTime.now(), FlightStatus.UNAVAILABLE, 180.0f, 80, 120);
        FlightDto result = flightService.updateFlight(1L, updatedFlightDto);

        assertNotNull(result);
        assertEquals(updatedFlightDto.idFlight(), result.idFlight());
        assertEquals(FlightStatus.UNAVAILABLE, result.status());
    }*/

    @Test
    void testDeleteById() {
        when(flightRepository.existsById(1L)).thenReturn(true);

        flightService.deleteById(1L);

        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFlightNotFound() {
        when(flightRepository.existsById(1L)).thenReturn(false);

        assertThrows(FlightNotFoundException.class, () -> flightService.deleteById(1L));
    }
}
