package com.sheoanna.airline.flights;

import com.sheoanna.airline.flights.dtos.FlightRequest;
import com.sheoanna.airline.flights.dtos.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api-endpoint}/private/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("")
    public Page<FlightResponse> showAllFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size/*, Sort.by(sortBy)*/);
        return flightService.findAllFlights(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> showFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.findFlightById(id));
    }

    @PostMapping("")
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest newFlightData) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.storeFlight(newFlightData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlightById(@PathVariable Long id, @RequestBody FlightRequest flightUpdatedData) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightUpdatedData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightById(@PathVariable Long id) {
        flightService.deleteFlightById(id);
        return ResponseEntity.noContent().build();
    }
}
