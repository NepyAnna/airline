package com.sheoanna.airline.airport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("removal")
@WebMvcTest(AirportController.class)
@AutoConfigureMockMvc(addFilters = false)

class AirportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAirports() throws Exception {
        List<AirportDto> airports = List.of(new AirportDto(1L, "JFK", "John F. Kennedy International Airport"));
        when(airportService.getAll()).thenReturn(airports);
    
        mockMvc.perform(get("/api/v1/private/airports"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAirportById() throws Exception {
        AirportDto airport = new AirportDto(1L, "JFK", "John F. Kennedy International Airport");
        when(airportService.getById(1L)).thenReturn(airport);

        mockMvc.perform(get("/api/v1/private/airports/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateAirport() throws Exception {
        AirportDto newAirport = new AirportDto(null, "LAX", "Los Angeles International Airport");
        AirportDto savedAirport = new AirportDto(2L, "LAX", "Los Angeles International Airport");
        when(airportService.store(any(AirportDto.class))).thenReturn(savedAirport);

        mockMvc.perform(post("/api/v1/private/airports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newAirport)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAirport() throws Exception {
        AirportDto updatedAirport = new AirportDto(1L, "JFK", "Updated Name");
        when(airportService.updateAirportData(eq(1L), any(AirportDto.class))).thenReturn(updatedAirport);

        mockMvc.perform(put("/api/v1/private/airports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedAirport)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAirport() throws Exception {
        doNothing().when(airportService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/private/airports/1"))
                .andExpect(status().isNoContent());
    }
}