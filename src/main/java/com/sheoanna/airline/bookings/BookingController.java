package com.sheoanna.airline.bookings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api-endpoint/bookings")
public class BookingController {
    private BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Booking> index() {
        List<Booking> bookings = service.findAll();
        return bookings;
    }
    
}
