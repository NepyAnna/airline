package com.sheoanna.airline.airport;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name_airport", length = 100)
    private String nameAirport;

    @Column(name = "code_iata_airport", length = 10)
    private String code_iata;

    public Airport(){}
}
