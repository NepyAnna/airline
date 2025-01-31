package com.sheoanna.airline.bookings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("${api-endpoint}/bookings")
public class BookingController {
    private BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<BookingDto> index() {
        List<BookingDto> bookings = service.getAll();
        return bookings;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> showByBookingId(@PathVariable Long id) {
        BookingDto bookingDto = service.getById(id);
        return ResponseEntity.ok(bookingDto);
    }

    @PostMapping("")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto newBookingDtoData) {
        BookingDto saveBookingDto = service.createBooking(newBookingDtoData);

        return ResponseEntity.ok(saveBookingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> putBookingById(@PathVariable Long id,
            @RequestBody BookingDto bookingDtoUpdateData) {
        BookingDto updatedBooking = service.updateBooking(id, bookingDtoUpdateData);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingById(@PathVariable Long id) {
        service.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
