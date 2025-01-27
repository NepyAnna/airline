package com.sheoanna.airline.airport;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("${api-endpoint}/airports")
public class AirportController {
    private AirportService service;

    public AirportController(AirportService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<AirportDto>> index() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDto> show(@PathVariable Long id) {
        AirportDto airport = service.getById(id);
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/code/{codeIata}")
    public ResponseEntity<AirportDto> showByIata(@PathVariable String codeIata) {
        AirportDto airportDto = service.getByCodeIata(codeIata);
        return ResponseEntity.ok(airportDto);
    }

    @PostMapping("")
    public ResponseEntity<AirportDto> create(@RequestBody AirportDto newAirportDtoData){
        AirportDto createdAirportDto = service.store(newAirportDtoData);
        return ResponseEntity.ok(createdAirportDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportDto> putAirportById(@PathVariable Long id, @RequestBody AirportDto airportDtoUpdateData) {
        AirportDto updatedAirportDto = service.updateAirportData(id,airportDtoUpdateData);
        return ResponseEntity.ok(updatedAirportDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAiportById(@PathVariable Long id) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
       
    }

}
