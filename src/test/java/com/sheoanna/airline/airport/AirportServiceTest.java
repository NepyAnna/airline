package com.sheoanna.airline.airport;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    private AirportRepository repository;

    @InjectMocks
    private AirportService airportService;

    @BeforeEach
    public void setUp() {
        this.airportService = new AirportService(repository);
    }

    @Test
    public void testGetAll() {
        List<Airport> airportList = new ArrayList<>();
        Airport airport1 = new Airport(1L, "Airport1", "AAA");
        Airport airport2 = new Airport(2L, "Airport2", "BBB");
        airportList.add(airport1);
        airportList.add(airport2);

        List<AirportDto> airportListDto = airportList.stream()
                .map(airport -> new AirportDto(airport.getIdAirport(), airport.getNameAirport(), airport.getCodeIata()))
                .toList();

        when(airportService.getAll()).thenReturn(airportList);

        List<Airport> result = airportService.getAll();

        assertEquals(2, result.size());
        assertEquals("AAA", result.get(0).getCodeIata());
        assertEquals("Airport1", result.get(0).getNameAirport());
    }

    @Test
    public void testGetById_Success() {
        Airport airport = new Airport(1L, "Airport1", "AAA");
        when(repository.findById(1L)).thenReturn(Optional.of(airport));

        AirportDto result = airportService.getById(1L);

        assertEquals(1L, result.idAirport());
        assertEquals("Airport1", result.nameAirport());
    }

    @Test
    public void testGetById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        AirportNotFoundException exception = assertThrows(AirportNotFoundException.class, () -> {
            airportService.getById(1L);
        });

        assertEquals("Airport with id 1 not found", exception.getMessage());
    }

    @Test
    public void testGetByCodeIata_Success() {
        Airport airport = new Airport(1L, "Airport1", "AAA");
        when(repository.findByCodeIata("AAA")).thenReturn(airport);

        AirportDto result = airportService.getByCodeIata("AAA");

        assertEquals(1L, result.idAirport());
        assertEquals("Airport1", result.nameAirport());
    }

    @Test
    public void testStore_Success() {
        AirportDto newAirportData = new AirportDto(1L, "New Airport", "CCC");
        Airport airport = new Airport(newAirportData.nameAirport(), newAirportData.codeIata());

        when(repository.findByCodeIata("CCC")).thenReturn(null);
        when(repository.save(any(Airport.class))).thenReturn(airport);

        AirportDto result = airportService.store(newAirportData);

        assertNotNull(result);
        assertEquals("New Airport", result.nameAirport());
        assertEquals("CCC", result.codeIata());
    }

    @Test
    public void testStore_AirportAlreadyExists() {
        AirportDto newAirportData = new AirportDto(null, "New Airport", "CCC");
        Airport existingAirport = new Airport(1L, "Existing Airport", "CCC");

        when(repository.findByCodeIata("CCC")).thenReturn(existingAirport);

        AirportAlreadyExistsException exception = assertThrows(AirportAlreadyExistsException.class, () -> {
            airportService.store(newAirportData);
        });

        assertEquals("Airport with cod: CCC already exists!", exception.getMessage());
    }

    @Test
    public void testUpdateAirportData_Success() {
        AirportDto updateData = new AirportDto(1l, "Updated Airport", "DDD");
        Airport existingAirport = new Airport(1L, "Airport1", "AAA");

        when(repository.findById(1L)).thenReturn(Optional.of(existingAirport));
        when(repository.save(any(Airport.class))).thenReturn(existingAirport);

        AirportDto result = airportService.updateAirportData(1L, updateData);

        assertEquals("Updated Airport", result.nameAirport());
        assertEquals("DDD", result.codeIata());
    }

    @Test
    public void testUpdateAirportData_NotFound() {
        AirportDto updateData = new AirportDto(1l, "Updated Airport", "DDD");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        AirportNotFoundException exception = assertThrows(AirportNotFoundException.class, () -> {
            airportService.updateAirportData(1L, updateData);
        });

        assertEquals("Airport with id 1 not found", exception.getMessage());
    }

    @Test
    public void testDeleteById_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        airportService.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        AirportNotFoundException exception = assertThrows(AirportNotFoundException.class, () -> {
            airportService.deleteById(1L);
        });

        assertEquals("Airport with id 1 not found", exception.getMessage());
    }
}
