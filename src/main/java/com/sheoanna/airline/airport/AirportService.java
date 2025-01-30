package com.sheoanna.airline.airport;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;

@Service
public class AirportService {
    private AirportRepository repository;

    public AirportService(AirportRepository repository) {
        this.repository = repository;
    }

    public List<Airport> getAll() {
        List<Airport> airports = repository.findAll();
        
        List<AirportDto> airportsDto = airports.stream().map(airport -> new AirportDto(
            airport.getIdAirport(),
            airport.getNameAirport(),
            airport.getCodeIata())).toList();

        return airports;
    }

    public AirportDto getById(Long id) {
        Airport airport = repository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException("Airport with id " + id + " not found"));
        AirportDto airportDto = new AirportDto(airport.getIdAirport(), airport.getNameAirport(), airport.getCodeIata());
        return airportDto;
    }

    public AirportDto getByCodeIata(String code) {
        Airport airport = repository.findByCodeIata(code);
        AirportDto airportDto = new AirportDto(airport.getIdAirport(), airport.getNameAirport(), airport.getCodeIata());
        return airportDto;
    }

    @Transactional
    public AirportDto store(AirportDto newAirportData) {
        Airport airport = new Airport(newAirportData.nameAirport(), newAirportData.codeIata());
        if (repository.findByCodeIata(airport.getCodeIata()) != null) {
            throw new AirportAlreadyExistsException("Airport with cod: " + airport.getCodeIata() + " already exists!");
        }
        Airport savedAirport = repository.save(airport);
        return new AirportDto(savedAirport.getIdAirport(), savedAirport.getNameAirport(), savedAirport.getCodeIata());
    }

    @Transactional
    public AirportDto updateAirportData(Long id, AirportDto airportDtoUpdateData) {
        Airport existingAirport = repository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException("Airport with id " + id + " not found"));

        existingAirport.setNameAirport(airportDtoUpdateData.nameAirport());
        existingAirport.setCodeIata(airportDtoUpdateData.codeIata());

        Airport savedAirport = repository.save(existingAirport);
        return new AirportDto(savedAirport.getIdAirport(), savedAirport.getNameAirport(), savedAirport.getCodeIata());
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new AirportNotFoundException("Airport with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
