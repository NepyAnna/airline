package com.sheoanna.airline.airport;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCodeIata(String code);
}
