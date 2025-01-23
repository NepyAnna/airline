package com.sheoanna.airline.flights;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportDto;
import com.sheoanna.airline.airport.AirportRepository;

@Service
public class FlightService {
        private FlightRepository repository;
        private final AirportRepository airportRepository;

        public FlightService(FlightRepository repository, AirportRepository airportRepository) {
                this.repository = repository;
                this.airportRepository = airportRepository;
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
                                .orElseThrow(() -> new FlightNotFoundException("Airport not found by id"));

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
                // Завантаження аеропортів із бази даних
                Airport departureAirport = findAirportById(newFlightData.departureAirport().idAirport());
                Airport arrivalAirport = findAirportById(newFlightData.arrivalAirport().idAirport());

                // Створення нового рейсу
                Flight newFlight = new Flight(
                                departureAirport,
                                arrivalAirport,
                                newFlightData.dateFlight(),
                                newFlightData.statusFlight(),
                                newFlightData.price(),
                                newFlightData.availableSeats(),
                                newFlightData.totalSeats());

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
                                .orElseThrow(() -> new FlightNotFoundException("Flight not found by id"));

                Airport departureAirport = findAirportById(flightDtoUpdateData.departureAirport().idAirport());
                Airport arrivalAirport = findAirportById(flightDtoUpdateData.arrivalAirport().idAirport());

                existingFlight.setDepartureAirport(departureAirport);
                existingFlight.setArrivalAirport(arrivalAirport);
                existingFlight.setDateFlight(flightDtoUpdateData.dateFlight());
                existingFlight.setStatusFlight(flightDtoUpdateData.statusFlight());
                existingFlight.setPrice(flightDtoUpdateData.price());
                existingFlight.setAvailableSeats(flightDtoUpdateData.availableSeats());
                existingFlight.setTotalSeats(flightDtoUpdateData.totalSeats());

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
                        throw new RuntimeException("Flight with id " + id + " not found");
                }
                repository.deleteById(id);
        }

        private AirportDto airportToAirportDto(Airport airport) {
                AirportDto airportDto = new AirportDto(airport.getIdAirport(),
                                airport.getNameAirport(),
                                airport.getCodeIata());
                return airportDto;
        }

        private Airport findAirportById(Long id) {
                return airportRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
        }
}
