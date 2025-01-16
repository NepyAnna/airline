package com.sheoanna.airline.airport;

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
    private Long idAirport;

    @Column(name = "name_airport", nullable = false, length = 100)
    private String nameAirport;

    @Column(name = "code_iata_airport", length = 10)
    private String codeIata;

}
