package com.sheoanna.airline.flights;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sheoanna.airline.airport.AirportDto;

@WebMvcTest(FlightController.class)
@AutoConfigureMockMvc(addFilters = false)
class FlightControllerTest {

    @MockitoBean
    private FlightService flightService;

    @Autowired
    private MockMvc mockMvc;

    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
        AirportDto departureAirport = new AirportDto(1L, "Departure Airport", "DPT");
        AirportDto arrivalAirport = new AirportDto(2L, "Arrival Airport", "ARR");
        flightDto = new FlightDto(1L, departureAirport, arrivalAirport, LocalDateTime.now(), FlightStatus.AVAILABLE, 200.0f, 100, 150);
    }

    @Test
    void testGetAllFlights() throws Exception {
        when(flightService.getAll()).thenReturn(Collections.singletonList(flightDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/private/flights")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idFlight").value(flightDto.idFlight()));
    }

    @Test
    void testGetFlightById() throws Exception {
        when(flightService.getById(1L)).thenReturn(flightDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/private/flights/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFlight").value(flightDto.idFlight()));
    }

    @Test
    void testCreateFlight() throws Exception {
        when(flightService.store(any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/private/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idFlight\": 1, \"departureAirport\": {\"idAirport\": 1, \"nameAirport\": \"Departure\", \"codeIata\": \"DPT\"}, \"arrivalAirport\": {\"idAirport\": 2, \"nameAirport\": \"Arrival\", \"codeIata\": \"ARR\"}, \"dateFlight\": \"2025-01-30T12:00:00\", \"status\": \"AVAILABLE\", \"price\": 200, \"availableSeats\": 100, \"totalSeats\": 150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFlight").value(flightDto.idFlight()));
    }

    /*@Test
    void testUpdateFlight() throws Exception {
        when(flightService.updateFlight(1L, any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/private/flights/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idFlight\": 1, \"departureAirport\": {\"idAirport\": 1, \"nameAirport\": \"Departure\", \"codeIata\": \"DPT\"}, \"arrivalAirport\": {\"idAirport\": 2, \"nameAirport\": \"Arrival\", \"codeIata\": \"ARR\"}, \"dateFlight\": \"2025-01-30T12:00:00\", \"status\": \"AVAILABLE\", \"price\": 200, \"availableSeats\": 100, \"totalSeats\": 150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFlight").value(flightDto.idFlight()));
    }*/

    @Test
    void testDeleteFlight() throws Exception {
        doNothing().when(flightService).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/private/flights/1"))
                .andExpect(status().isNoContent());
    }
}