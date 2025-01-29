package com.sheoanna.airline.flights;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("${api-endpoint}/private/flights")
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

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> putFlightById(@PathVariable Long id, @RequestBody FlightDto flightUpdatedDataDto) {
       FlightDto updatedFlightDto = service.updateFlight(id,flightUpdatedDataDto);
        return ResponseEntity.ok(updatedFlightDto);
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightById(@PathVariable Long id) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
       
    }
}
