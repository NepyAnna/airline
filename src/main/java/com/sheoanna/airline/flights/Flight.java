package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import java.util.Set;
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
    @Column(nullable = false)//name = "flight_status",
    private FlightStatus statusFlight = FlightStatus.AVAILABLE;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @ManyToMany(mappedBy = "flights")
    private Set<Booking> bookings;

}