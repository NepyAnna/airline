package com.sheoanna.airline.bookings;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {
    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

     public List<Booking> findAll() {
        return repository.findAll();}
}
