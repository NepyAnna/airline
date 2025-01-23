package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
      List<Flight> findByDepartureAirport_CodeIataAndArrivalAirport_CodeIataAndDateFlightAndAvailableSeatsGreaterThanEqual(
            String departureAirportCode, 
            String arrivalAirportCode, 
            LocalDateTime dateFlight, 
            int requiredSeats
    );
    
}
