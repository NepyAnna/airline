package com.sheoanna.airline.flights;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.airport.AirportRepository;
import com.sheoanna.airline.airport.exceptions.AirportNotFoundException;
import com.sheoanna.airline.flights.exceptions.FlightNotFoundException;

@Service
public class FlightService {
        private FlightRepository repository;
        private AirportRepository airportRepository;

        public FlightService(FlightRepository repository) {
                this.repository = repository;

        }

        public List<FlightDto> getAll() {
                List<Flight> flights = repository.findAll();

                return flights.stream()
                                .map(flight -> new FlightDto(
                                                flight.getIdFlight(),
                                                airportToAirportDto(flight.getDepartureAirport()),
                                                airportToAirportDto(flight.getArrivalAirport()),
                                                flight.getDateFlight(),
                                                flight.getStatusFlight(),
                                                flight.getPrice(),
                                                flight.getAvailableSeats(),
                                                flight.getTotalSeats()))
                                .toList();
        }

        public FlightDto getById(Long id) {
                Flight flight = repository.findById(id)
                                .orElseThrow(() -> new FlightNotFoundException("Airport not found with id: " + id));

                FlightDto flightDto = new FlightDto(flight.getIdFlight(),
                                airportToAirportDto(flight.getDepartureAirport()),
                                airportToAirportDto(flight.getArrivalAirport()),
                                flight.getDateFlight(),
                                flight.getStatusFlight(),
                                flight.getPrice(),
                                flight.getAvailableSeats(),
                                flight.getTotalSeats());

                return flightDto;
        }

        @Transactional
        public FlightDto store(FlightDto newFlightData) {
                Airport departureAirport = findAirportById(newFlightData.departureAirport().idAirport());
                Airport arrivalAirport = findAirportById(newFlightData.arrivalAirport().idAirport());

                Flight newFlight = new Flight(
                                departureAirport,
                                arrivalAirport,
                                newFlightData.dateFlight(),
                                newFlightData.status(),
                                newFlightData.price(),
                                newFlightData.availableSeats(),
                                newFlightData.totalSeats());

                newFlight.updateStatusIfNeeded();
                Flight savedFlight = repository.save(newFlight);

                return new FlightDto(
                                savedFlight.getIdFlight(),
                                airportToAirportDto(savedFlight.getDepartureAirport()),
                                airportToAirportDto(savedFlight.getArrivalAirport()),
                                savedFlight.getDateFlight(),
                                savedFlight.getStatusFlight(),
                                savedFlight.getPrice(),
                                savedFlight.getAvailableSeats(),
                                savedFlight.getTotalSeats());
        }

        @Transactional
        public FlightDto updateFlight(Long id, FlightDto flightDtoUpdateData) {
                Flight existingFlight = repository.findById(id)
                                .orElseThrow(() -> new FlightNotFoundException("Flight with id " + id + " not found"));

                Airport departureAirport = findAirportById(flightDtoUpdateData.departureAirport().idAirport());
                Airport arrivalAirport = findAirportById(flightDtoUpdateData.arrivalAirport().idAirport());

                existingFlight.setDepartureAirport(departureAirport);
                existingFlight.setArrivalAirport(arrivalAirport);
                existingFlight.setDateFlight(flightDtoUpdateData.dateFlight());
                existingFlight.setStatusFlight(flightDtoUpdateData.status());
                existingFlight.setPrice(flightDtoUpdateData.price());
                existingFlight.setAvailableSeats(flightDtoUpdateData.availableSeats());
                existingFlight.setTotalSeats(flightDtoUpdateData.totalSeats());

                existingFlight.updateStatusIfNeeded();
                Flight savedFlight = repository.save(existingFlight);

                return new FlightDto(
                                savedFlight.getIdFlight(),
                                airportToAirportDto(savedFlight.getDepartureAirport()),
                                airportToAirportDto(savedFlight.getArrivalAirport()),
                                savedFlight.getDateFlight(),
                                savedFlight.getStatusFlight(),
                                savedFlight.getPrice(),
                                savedFlight.getAvailableSeats(),
                                savedFlight.getTotalSeats());
        }

        public void deleteById(Long id) {
                if (!repository.existsById(id)) {
                        throw new FlightNotFoundException("Flight with id " + id + " not found");
                }
                repository.deleteById(id);
        }

        private AirportDto airportToAirportDto(Airport airport) {
                return new AirportDto(
                                airport.getIdAirport(),
                                airport.getNameAirport(),
                                airport.getCodeIata());
        }

        private Airport findAirportById(Long id) {
                return airportRepository.findById(id)
                                .orElseThrow(() -> new AirportNotFoundException("Airport not found with id: " + id));
        }
}
