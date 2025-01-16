package com.sheoanna.airline.flights;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-endpoint/flights")
public class FlightController {
    private FlightService service;

    public FlightController(FlightService service){
        this.service = service;
    }

    @GetMapping("")
    public List<Flight> index() {
        List<Flight> flights = service.findAll();
        return flights;
    }
}
