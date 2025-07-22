package com.sheoanna.airline.flights;

import com.sheoanna.airline.airport.AirportService;
import com.sheoanna.airline.flights.dtos.FlightMapper;
import com.sheoanna.airline.flights.dtos.FlightRequest;
import com.sheoanna.airline.flights.dtos.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final FlightMapper flightMapper;

    public Page<FlightResponse> findAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable)
                .map(flightMapper::toResponse);
    }

    public FlightResponse findFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Airport not found with id: " + id));
        return flightMapper.toResponse(flight);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FlightResponse storeFlight(FlightRequest newFlightData) {
        Airport departureAirport = airportService.findObjByCodeIata(newFlightData.departureAirportIata());
        Airport arrivalAirport = airportService.findObjByCodeIata(newFlightData.arrivalAirportIata());

        Flight newFlight = flightMapper.toEntity(newFlightData);
        newFlight.setDepartureAirport(departureAirport);
        newFlight.setArrivalAirport(arrivalAirport);

        newFlight.updateStatusIfNeeded();
        Flight savedFlight = flightRepository.save(newFlight);

        return flightMapper.toResponse(savedFlight);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FlightResponse updateFlight(Long id, FlightRequest flightUpdateData) {
        Flight existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id " + id + " not found"));

        Airport departureAirport = airportService.findObjByCodeIata(flightUpdateData.departureAirportIata());
        Airport arrivalAirport = airportService.findObjByCodeIata(flightUpdateData.arrivalAirportIata());

        existingFlight.setDepartureAirport(departureAirport);
        existingFlight.setArrivalAirport(arrivalAirport);
        existingFlight.setDateFlight(flightUpdateData.dateFlight());
        existingFlight.setPrice(flightUpdateData.price());
        existingFlight.setStatus(flightUpdateData.status());
        existingFlight.setAvailableSeats(flightUpdateData.availableSeats());
        existingFlight.setTotalSeats(flightUpdateData.totalSeats());

        existingFlight.updateStatusIfNeeded();

        //Flight savedFlight = repository.save(existingFlight);// Transactional will save

        return flightMapper.toResponse(existingFlight);
    }

    public void deleteFlightById(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new FlightNotFoundException("Flight with id " + id + " not found");
        }
        flightRepository.deleteById(id);
    }
}
