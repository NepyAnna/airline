package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.bookings.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flight")
    private Long idFlight;

    @ManyToOne
    @JoinColumn(name = "id_departure_airport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "id_arrival_airport", nullable = false)
    private Airport arrivalAirport;

    @Column(name = "date_flight", length = 50)
    private LocalDateTime dateFlight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus statusFlight = FlightStatus.AVAILABLE;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "total_seats", length = 10)
    private int totalSeats;

    @ManyToMany(mappedBy = "flights")
    @JsonIgnore
    private Set<Booking> bookings;

    public Flight(Airport departureAirport, Airport arrivalAirport, LocalDateTime dateFlight, FlightStatus statusFlight,int availableSeats, int totalSeats) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.dateFlight = dateFlight;
        this.statusFlight = statusFlight;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.bookings = new HashSet<>();
    }
}