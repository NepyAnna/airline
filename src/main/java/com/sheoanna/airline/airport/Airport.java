package com.sheoanna.airline.airport;

import java.util.ArrayList;
import java.util.List;

import com.sheoanna.airline.flights.Flight;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "code_iata", unique = true, nullable = false)
    private String codeIata;

    @OneToMany(mappedBy = "departureAirport")
    private List<Flight> flightsDeparture = new ArrayList<>();

    @OneToMany(mappedBy = "arrivalAirport")
    private List<Flight> flightsArrival = new ArrayList<>();
}
