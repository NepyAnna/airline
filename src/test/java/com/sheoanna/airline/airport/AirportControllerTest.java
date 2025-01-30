package com.sheoanna.airline.airport;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AirportController.class)
@AutoConfigureMockMvc(addFilters = false)
class AirportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirportService airportService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testGetAllAirports() throws Exception {
        List<Airport> airports = new ArrayList<>();
        Airport first = new Airport(1L, "JFK", "John F. Kennedy International Airport");
        airports.add(first);

        when(airportService.getAll()).thenReturn(airports);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/private/airports")
                .accept(MediaType.APPLICATION_JSON)
                .content("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString(first.getCodeIata()));
        assertThat(response.getContentAsString(), equalTo(mapper.writeValueAsString(airports)));

    }

    @Test
    void testGetAirportById() throws Exception {
        AirportDto airport = new AirportDto(1L, "JFK", "John F. Kennedy International Airport");
        when(airportService.getById(1L)).thenReturn(airport);

        mockMvc.perform(get("/api/v1/private/airports/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testControllerHasPathToPostCountry() throws Exception {
        AirportDto airport = new AirportDto(1L, "JFK", "John F. Kennedy International Airport");

        String json = mapper.writeValueAsString(airport);

        when(airportService.store(Mockito.any(AirportDto.class))).thenReturn(airport);
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/private/airports")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), is(json));

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