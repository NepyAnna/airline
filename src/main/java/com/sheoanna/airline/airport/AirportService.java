package com.sheoanna.airline.airport;

import com.sheoanna.airline.airport.dtos.AirportMapper;
import com.sheoanna.airline.airport.dtos.AirportRequest;
import com.sheoanna.airline.airport.dtos.AirportResponse;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final UserService userService;
    private final AirportMapper airportMapper;

    public Page<AirportResponse> findAll(Pageable pageable) {
        return airportRepository.findAll(pageable)
                .map(airportMapper::toResponse);
    }

    public AirportResponse findById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException(id));
        return airportMapper.toResponse(airport);
    }

    public AirportResponse findByCodeIata(String code) {
        Airport airport = airportRepository.findByCodeIata(code)
                .orElseThrow(() -> new AirportNotFoundException(code));
        return airportMapper.toResponse(airport);
    }

    @Transactional
    public AirportResponse store(AirportRequest newAirportData) {
        Airport airport = airportMapper.toEntity(newAirportData);
        if (airportRepository.findByCodeIata(airport.getCodeIata()).isPresent()) {
            throw new AirportAlreadyExistsException(airport.getCodeIata());
        }
        Airport savedAirport = airportRepository.save(airport);
        return airportMapper.toResponse(savedAirport);
    }

    @Transactional
    public AirportResponse updateAirportData(Long id, AirportRequest airportDtoUpdateData) {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException(id));

        existingAirport.setName(airportDtoUpdateData.name());
        existingAirport.setCodeIata(airportDtoUpdateData.codeIata());

        Airport savedAirport = airportRepository.save(existingAirport);
        return airportMapper.toResponse(savedAirport);
    }

    public void deleteById(Long id) {
        User user = userService.getAuthenticatedUser();
        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("You are not allowed to delete airport.");
        }
        if (!airportRepository.existsById(id)) {
            throw new AirportNotFoundException(id);
        }
        airportRepository.deleteById(id);
    }

    public Airport findObjByCodeIata(String code) {
        return airportRepository.findByCodeIata(code)
                .orElseThrow(() -> new AirportNotFoundException(code));
    }

    public void validateIataCodes(String departureCodeIata, String arrivalCodeIata) {
        if (departureCodeIata.equals(arrivalCodeIata)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Departure and arrival airports must be differ"
            );
        }

        if (airportRepository.findByCodeIata(departureCodeIata).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown departure airport: " + departureCodeIata);
        }

        if (airportRepository.findByCodeIata(arrivalCodeIata).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown arrival airport: " + arrivalCodeIata);
        }
    }
}
