package com.sheoanna.airline.flights;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api-endpoint/flights")
public class FlightController {
    private FlightService service;

    public FlightController(FlightService service){
        this.service = service;
    }

    @GetMapping("")
    public List<FlightDto> index() {
        List<FlightDto> flights = service.getAll();
        return flights;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> showById(@PathVariable Long id) {
        FlightDto flightDto = service.getById(id);
        return ResponseEntity.ok(flightDto);
    }
    
    @PostMapping("")
    public ResponseEntity<FlightDto> create(@RequestBody FlightDto newFlightDtoData) {
        FlightDto createFlightDto = service.store(newFlightDtoData);
        return ResponseEntity.ok(createFlightDto);
    }
    
}
