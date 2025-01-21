package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sheoanna.airline.flights.Flight;
import com.sheoanna.airline.users.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_booking")
    private Long idBooking;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany
    @JoinTable(name = "bookings_flights", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "flight_id"))
    @JsonIgnore
    private List<Flight> flights;

    @Column(name = "date_booking", length = 50)
    private LocalDateTime dateBooking;

    @Column(name = "booked_seats", length = 50)
    private int bookedSeats;

    public Booking(User user, List<Flight> flights, LocalDateTime dateBooking, int bookedSeats) {
        this.user = user;
        this.flights = flights;
        this.dateBooking = dateBooking;
        this.bookedSeats = bookedSeats;
    }


    /*@Enumerated(EnumType.STRING)  // Enums для статусу
    private BookingStatus status = BookingStatus.PENDING;*/

}
