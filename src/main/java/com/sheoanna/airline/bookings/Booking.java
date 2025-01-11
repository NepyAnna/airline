package com.sheoanna.airline.bookings;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.users.User;

import jakarta.persistence.*;

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Long id_booking;


    @ManyToOne()
    @JoinColumn(name = "id_user", nullable = true)
    @JsonManagedReference
    private User user;

    @OneToMany()
    @JoinColumn(name = "id_flights", nullable = true)
    private Flight flight;

    public Booking(){} 
}
