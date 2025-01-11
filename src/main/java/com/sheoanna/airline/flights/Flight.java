package com.sheoanna.airline.flights;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

import com.sheoanna.airline.airport.Airport;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flight")
    private Long id_flight;

    @Column(name = "is_flight_avalible", nullable = false, length = 100)
    private boolean isAvaliable;

    @Column(name = "flight_date_time", nullable = false)
    private LocalDateTime flightDataTime;

    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Column(name = "departure_airport", length = 100)//code_aiti or id_airoport????
    private Airport departureAirport;

    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Column(name = "destination_airport", length = 100)//code_aiti or id_airoport ???
    private Airport destinationAirport;

    @Column(name = "total_seats", length = 10)
    private int totalSeats;

    //avalibleSeats!!!???

    public Flight() {
    }

}
