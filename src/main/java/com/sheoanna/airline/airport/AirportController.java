package com.sheoanna.airline.airport;

import com.sheoanna.airline.airport.dtos.AirportRequest;
import com.sheoanna.airline.airport.dtos.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api-endpoint}/private/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping("")
    public Page<AirportResponse> showAllAiroports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size/*,
            @RequestParam(defaultValue = "id") String sortBy*/
    ) {
        Pageable pageable = PageRequest.of(page, size/*, Sort.by(sortBy)*/);
        return airportService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> showById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.findById(id));
    }

    @GetMapping("/code/{codeIata}")
    public ResponseEntity<AirportResponse> showByIata(@PathVariable String codeIata) {
        return ResponseEntity.ok(airportService.findByCodeIata(codeIata));
    }

    @PostMapping("")
    public ResponseEntity<AirportResponse> create(@RequestBody AirportRequest newAirportDtoData) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(airportService.store(newAirportDtoData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirportById(@PathVariable Long id,
                                                             @RequestBody AirportRequest airportUpdateData) {
        return ResponseEntity.ok(airportService.updateAirportData(id, airportUpdateData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAiportById(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
