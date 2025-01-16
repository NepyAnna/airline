package com.sheoanna.airline.flights;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FlightService {
    private FlightRepository repository;

    //@PersistenceContext
    //private EntityManager entityManager;

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }


    public List<Flight> findAll() {
        return repository.findAll();}
    /*public List<FlightDto> findAll() {
        return repository.findAll().stream()
        .map(flight -> new FlightDto(
                flight.getId(),
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getFlightDate(),
                flight.getStatus(),
                flight.getAvailableSeats(), 
                flight.getBookings() != null ? flight.getBookings().forEach(<Booking>.getId()) : null))
        .toList();
    }*/
    
}
