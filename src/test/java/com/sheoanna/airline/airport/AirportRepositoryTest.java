package com.sheoanna.airline.airport;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class AirportRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AirportRepository repository;


    
    @Test
    @DisplayName("Find all countries")
    void testGetAllAirports() {
        Airport air = new Airport(1l, "Los Angeles International Airport", "FFF");

        entityManager.persist(air);

        List<Airport> airports1 = repository.findAll();
        assertThat(airports1.get(0).getCodeIata()).isEqualTo("FFF");
        assertThat(airports1.get(0).getNameAirport()).isEqualTo("Los Angeles International Airport");

    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }
}