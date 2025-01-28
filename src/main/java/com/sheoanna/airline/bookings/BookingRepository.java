package com.sheoanna.airline.bookings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    /*@Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.flight")
    List<Booking> findAllWithDetails();*/
    
}
