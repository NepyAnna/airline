package com.sheoanna.airline.airport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sheoanna.airline.flights.Flight;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_airport")
    private Long idAirport;

    @Column(name = "name_airport", nullable = false, length = 100)
    private String nameAirport;

    @Column(name = "code_iata_airport", unique = true, nullable = false)
    private String codeIata;

    @OneToMany(mappedBy = "departureAirport")
    @JsonManagedReference
    private List<Flight> flightsDeparture;

    @OneToMany(mappedBy = "arrivalAirport")
    @JsonManagedReference
    private List<Flight> flightsArrival;

    public Airport(String nameAirport, String codeIata) {
        this.nameAirport = nameAirport;
        this.codeIata = codeIata;
    }
}
