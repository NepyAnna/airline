package com.sheoanna.airline.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.flight")
    List<Booking> findAllWithDetails();
}
