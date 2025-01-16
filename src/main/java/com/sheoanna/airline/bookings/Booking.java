package com.sheoanna.airline.bookings;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Long idBooking;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany
    @JoinTable(name = "bookings_flights", joinColumns = @JoinColumn(name = "id_booking"), inverseJoinColumns = @JoinColumn(name = "id_flight"))
    private Set<Flight> flights;

    @Column(name = "date_booking", length = 50)
    private LocalDateTime dateBooking;

    @Column(name = "booked_seats", length = 10)
    private int bookedSeats;

    @Column(name = "total_seats", length = 10)
    private int totalSeats;

    /*@Enumerated(EnumType.STRING)  // Enums для статусу
    private BookingStatus status = BookingStatus.PENDING;*/

}
