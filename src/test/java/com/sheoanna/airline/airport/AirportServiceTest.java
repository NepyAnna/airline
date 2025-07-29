package com.sheoanna.airline.airport;

import com.sheoanna.airline.airport.dtos.AirportMapper;
import com.sheoanna.airline.airport.dtos.AirportRequest;
import com.sheoanna.airline.airport.dtos.AirportResponse;
import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;

    @Mock
    private UserService userService;

    @Mock
    private AirportMapper airportMapper;

    @InjectMocks
    private AirportService airportService;

    @Test
    void findAll_shouldReturnPageOfAirports() {
        Pageable pageable = PageRequest.of(0, 2);
        Airport airport = new Airport();
        AirportResponse response = new AirportResponse(1L, "Test Airport", "TST");
        when(airportRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(airport)));
        when(airportMapper.toResponse(airport)).thenReturn(response);

        Page<AirportResponse> result = airportService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(airportRepository).findAll(pageable);
    }

    @Test
    void findById_shouldReturnAirport_whenFound() {
        Airport airport = new Airport(1L, "Test", "TST", List.of(), List.of());
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportMapper.toResponse(airport)).thenReturn(new AirportResponse(1L, "Test", "TST"));

        AirportResponse response = airportService.findById(1L);

        assertEquals("Test", response.name());
        verify(airportRepository).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(airportRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AirportNotFoundException.class, () -> airportService.findById(1L));
    }

    @Test
    void store_shouldCreateAirport_whenCodeIsUnique() {
        AirportRequest request = new AirportRequest("New Airport", "NEW");
        Airport airport = new Airport(null, "New Airport", "NEW", List.of(), List.of());
        Airport savedAirport = new Airport(1L, "New Airport", "NEW", List.of(), List.of());
        when(airportMapper.toEntity(request)).thenReturn(airport);
        when(airportRepository.findByCodeIata("NEW")).thenReturn(Optional.empty());
        when(airportRepository.save(airport)).thenReturn(savedAirport);
        when(airportMapper.toResponse(savedAirport)).thenReturn(new AirportResponse(1L, "New Airport", "NEW"));

        AirportResponse response = airportService.store(request);

        assertEquals("New Airport", response.name());
    }

    @Test
    void store_shouldThrowException_whenCodeExists() {
        AirportRequest request = new AirportRequest("Duplicate", "DUP");
        when(airportMapper.toEntity(request)).thenReturn(new Airport(null, "Duplicate", "DUP", List.of(), List.of()));
        when(airportRepository.findByCodeIata("DUP")).thenReturn(Optional.of(new Airport()));

        assertThrows(AirportAlreadyExistsException.class, () -> airportService.store(request));
    }

    @Test
    void updateAirportData_shouldUpdateAirport() {
        Long id = 1L;
        AirportRequest updateRequest = new AirportRequest("Updated", "UPD");
        Airport existing = new Airport(id, "Old", "OLD", List.of(), List.of());
        Airport updated = new Airport(id, "Updated", "UPD", List.of(), List.of());

        when(airportRepository.findById(id)).thenReturn(Optional.of(existing));
        when(airportRepository.save(existing)).thenReturn(updated);
        when(airportMapper.toResponse(updated)).thenReturn(new AirportResponse(id, "Updated", "UPD"));

        AirportResponse response = airportService.updateAirportData(id, updateRequest);

        assertEquals("Updated", response.name());
    }

    @Test
    void deleteById_shouldDeleteAirport_whenUserIsAdmin() {
        Long id = 1L;
        User user = new User();
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(userService.isAdmin(user)).thenReturn(true);
        when(airportRepository.existsById(id)).thenReturn(true);

        airportService.deleteById(id);

        verify(airportRepository).deleteById(id);
    }

    @Test
    void deleteById_shouldThrowAccessDenied_whenNotAdmin() {
        User user = new User();
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(userService.isAdmin(user)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> airportService.deleteById(1L));
    }

    @Test
    void validateIataCodes_shouldThrowException_whenSameCodes() {
        String same = "ABC";

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> airportService.validateIataCodes(same, same));

        assertEquals("400 BAD_REQUEST \"Departure and arrival airports must be differ\"", ex.getMessage());
    }

    @Test
    void validateIataCodes_shouldThrow_whenDepartureUnknown() {
        when(airportRepository.findByCodeIata("AAA")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> airportService.validateIataCodes("AAA", "BBB"));

        assertTrue(ex.getMessage().contains("Unknown departure airport"));
    }

    @Test
    void validateIataCodes_shouldThrow_whenArrivalUnknown() {
        when(airportRepository.findByCodeIata("AAA")).thenReturn(Optional.of(new Airport()));
        when(airportRepository.findByCodeIata("BBB")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> airportService.validateIataCodes("AAA", "BBB"));

        assertTrue(ex.getMessage().contains("Unknown arrival airport"));
    }
}