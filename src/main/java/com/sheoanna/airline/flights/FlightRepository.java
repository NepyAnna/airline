package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {
      @Query("SELECT f FROM Flight f WHERE f.departureAirport.idAirport = :departureAirportId " +
                  "AND f.arrivalAirport.idAirport = :arrivalAirportId " +
                  "AND f.dateFlight = :flightDate " +
                  "AND f.availableSeats >= :requiredSeats")
      Optional<Flight> findFlightByParameters(@Param("departureAirportId") Long departureAirportId,
                  @Param("arrivalAirportId") Long arrivalAirportId,
                  @Param("flightDate") LocalDateTime flightDate,
                  @Param("requiredSeats") int requiredSeats);

}
/*
 * 
 * 
 * 
 * 
 * List<Flight>
 * findByDepartureAirport_CodeIataAndArrivalAirport_CodeIataAndDateFlightAndAvailableSeatsGreaterThanEqual(
 * String departureAirportCode,
 * String arrivalAirportCode,
 * LocalDateTime dateFlight,
 * int requiredSeats
 * );
 */