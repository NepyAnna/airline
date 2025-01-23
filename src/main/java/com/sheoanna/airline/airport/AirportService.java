package com.sheoanna.airline.airport;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirportService {
    private AirportRepository repository;

    public AirportService(AirportRepository repository) {
        this.repository = repository;
    }

    public List<AirportDto> getAll() {
        List<AirportDto> airportsDto = repository.findAll().stream().map(airport -> new AirportDto(
                airport.getIdAirport(),
                airport.getNameAirport(),
                airport.getCodeIata()))
                .toList();
        return airportsDto;
    }

    public AirportDto getById(Long id) {
        Airport airport = repository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException("Airport not found by id"));
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
        Airport airport = new Airport(newAirportData.nameAirport(),newAirportData.codeIata());
        Airport savedAirport = repository.save(airport);
        return new AirportDto(savedAirport.getIdAirport(), savedAirport.getNameAirport(), savedAirport.getCodeIata());
    }

    @Transactional
    public AirportDto updateAirportData(Long id, AirportDto airportDtoUpdateData) {
        Airport existingAirport = repository.findById(id)
        .orElseThrow(() -> new AirportNotFoundException("Airport not found by id"));

        existingAirport.setNameAirport(airportDtoUpdateData.nameAirport());
        existingAirport.setCodeIata(airportDtoUpdateData.codeIata());
        

        Airport savedAirport = repository.save(existingAirport);
        return new AirportDto(savedAirport.getIdAirport(), savedAirport.getNameAirport(), savedAirport.getCodeIata());
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Airport with id " + id + " not found");
        }
        repository.deleteById(id);
    }

}
