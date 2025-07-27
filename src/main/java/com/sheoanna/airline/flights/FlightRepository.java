package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("""
    SELECT f FROM Flight f
    WHERE f.departureAirport.codeIata = :departureCodeIata
      AND f.arrivalAirport.codeIata = :arrivalCodeIata
      AND f.dateFlight = :dateFlight
      AND f.availableSeats >= :seats
""")
    Optional<Flight> findFlightByParameters(
            @Param("departureCodeIata") String departureCodeIata,
            @Param("arrivalCodeIata") String arrivalCodeIata,
            @Param("dateFlight") LocalDateTime dateFlight,
            @Param("seats") int seats
    );
}