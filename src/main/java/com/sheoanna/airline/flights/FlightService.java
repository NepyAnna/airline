package com.sheoanna.airline.flights;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class FlightService {
    private FlightRepository repository;

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }

     public List<FlightDto> getAll() {
        List<Flight> flights = repository.findAll();

        return flights.stream()
                .map(flight -> new FlightDto(
                        flight.getIdFlight(),
                        flight.getDepartureAirport(),
                        flight.getArrivalAirport(),
                        flight.getDateFlight(),
                        flight.getStatusFlight(),
                        flight.getPrice(),
                        flight.getAvailableSeats(),
                        flight.getTotalSeats()))
                .toList();
    }

    public FlightDto getById(Long id) {
        Flight flight = repository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Airport not found by id"));

        FlightDto flightDto = new FlightDto(flight.getIdFlight(),
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDateFlight(),
                flight.getStatusFlight(),
                flight.getPrice(),
                flight.getAvailableSeats(),
                flight.getTotalSeats());

        return flightDto;
    }

    @Transactional
    public FlightDto store(FlightDto newFlightData) {
        Flight newFlight = new Flight(newFlightData.departureAirport(), newFlightData.arrivalAirport(),
                newFlightData.flightDate(), newFlightData.status(), newFlightData.price(),
                newFlightData.availableSeats(), newFlightData.totalSeats());

        Flight savedFlight = repository.save(newFlight);

        return new FlightDto(savedFlight.getIdFlight(),
                savedFlight.getDepartureAirport(),
                savedFlight.getArrivalAirport(),
                savedFlight.getDateFlight(),
                savedFlight.getStatusFlight(),
                savedFlight.getPrice(),
                savedFlight.getAvailableSeats(),
                savedFlight.getTotalSeats());
    }

    @Transactional
    public FlightDto updateFlight(Long id, FlightDto flightDtoUpdateData) {
        Flight existingFlight = repository.findById(id)
        .orElseThrow(() -> new FlightNotFoundException("Flight not found by id"));

        existingFlight.setDepartureAirport(flightDtoUpdateData.departureAirport());
        existingFlight.setArrivalAirport(flightDtoUpdateData.arrivalAirport());
        existingFlight.setDateFlight(flightDtoUpdateData.flightDate());
        existingFlight.setStatusFlight(flightDtoUpdateData.status());
        existingFlight.setPrice(flightDtoUpdateData.price());
        existingFlight.setAvailableSeats(flightDtoUpdateData.availableSeats());
        existingFlight.setTotalSeats(flightDtoUpdateData.totalSeats());
        
        Flight savedFlight = repository.save(existingFlight);

        return new FlightDto(savedFlight.getIdFlight(),
        savedFlight.getDepartureAirport(),
        savedFlight.getArrivalAirport(),
        savedFlight.getDateFlight(),
        savedFlight.getStatusFlight(),
        savedFlight.getPrice(),
        savedFlight.getAvailableSeats(),
        savedFlight.getTotalSeats());
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
                throw new RuntimeException("Flight with id " + id + " not found");
            }
            repository.deleteById(id);
    }
}
