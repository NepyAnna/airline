package com.sheoanna.airline.airport;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AirportRepository extends JpaRepository<Airport, Long>{
    //@Query("select a from Airport a where a.codeIata = :codeIata")
    Airport findByCodeIata(String codeIata);
}
