package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    @Column(name = "date_booking", length = 50)
    private LocalDateTime dateBooking;

    @Column(name = "booked_seats", length = 50)
    private int bookedSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flight", nullable = false)
    @JsonBackReference
    private Flight flight;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
    
    public Booking(User user, LocalDateTime dateBooking, int bookedSeats, Flight flight) {
        this.user = user;
        this.flight = flight;
        this.dateBooking = dateBooking;
        this.bookedSeats =bookedSeats;
    }


    

}
