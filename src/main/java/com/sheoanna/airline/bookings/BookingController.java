package com.sheoanna.airline.bookings;

import com.sheoanna.airline.bookings.dtos.BookingRequest;
import com.sheoanna.airline.bookings.dtos.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("${api-endpoint}/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("")
    public Page<BookingResponse> showAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size/*,
            @RequestParam(defaultValue = "id") String sortBy*/
    ) {
        Pageable pageable = PageRequest.of(page, size/*, Sort.by(sortBy)*/);
        return bookingService.findAllBookings(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> showByBookingId(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingById(id));
    }

    @PostMapping("")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest newBookingData) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(newBookingData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> putBookingById(@PathVariable Long id,
                                                          @RequestBody BookingRequest bookingUpdateData) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingUpdateData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
