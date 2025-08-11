package com.sheoanna.airline.airport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheoanna.airline.airport.dtos.AirportRequest;
import com.sheoanna.airline.flights.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class AirportControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private FlightRepository flightRepository;

    private final String BASE_URL = "/api/v1/private/airports";

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateAirport() throws Exception {
        AirportRequest request = new AirportRequest("Kharkiv Intl", "HRK");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kharkiv Intl"))
                .andExpect(jsonPath("$.codeIata").value("HRK"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAirportById() throws Exception {
        Airport airport = airportRepository.save(new Airport(null, "Ivano-Frankivsk", "IFO", null, null));

        mockMvc.perform(get(BASE_URL + "/" + airport.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivano-Frankivsk"))
                .andExpect(jsonPath("$.codeIata").value("IFO"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateAirport() throws Exception {
        Airport airport = airportRepository.save(new Airport(null, "Old Airport", "OLD", null, null));
        AirportRequest update = new AirportRequest("New Airport", "NEW");

        mockMvc.perform(put(BASE_URL + "/" + airport.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Airport"))
                .andExpect(jsonPath("$.codeIata").value("NEW"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllAirports() throws Exception {
        airportRepository.save(new Airport(null, "Airport 1", "A01", null, null));
        airportRepository.save(new Airport(null, "Airport 2", "A02", null, null));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetByCodeIata() throws Exception {
        Airport airport = new Airport();
        airport.setName("Dnipro Airport");
        airport.setCodeIata("DNK");
        airportRepository.save(airport);

        mockMvc.perform(get(BASE_URL + "/code/DNK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dnipro Airport"));
    }
}
