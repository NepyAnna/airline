package com.sheoanna.airline.schedulers;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.flights.FlightRepository;

@EnableScheduling
@Component
public class FlightStatusScheduler {
    private final FlightRepository flightRepository;

    public FlightStatusScheduler(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateFlightStatuses() {
        List<Flight> flights = flightRepository.findAll();
        for (Flight flight : flights) {
            flight.updateStatusIfNeeded();
        }
        flightRepository.saveAll(flights);
    }
}
