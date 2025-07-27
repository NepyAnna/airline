package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.bookings.Booking;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_departure_airport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "id_arrival_airport", nullable = false)
    private Airport arrivalAirport;

    private LocalDateTime dateFlight;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.AVAILABLE;

    private double price;
    private int availableSeats;
    private int totalSeats;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    public void updateStatusIfNeeded() {
        if (availableSeats == 0 || dateFlight.isBefore(LocalDateTime.now())) {
            status = FlightStatus.UNAVAILABLE;
        } else {
            status = FlightStatus.AVAILABLE;
        }
    }
}