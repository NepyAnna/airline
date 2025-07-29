package com.sheoanna.airline.flights;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheoanna.airline.airport.Airport;
import com.sheoanna.airline.airport.AirportRepository;
import com.sheoanna.airline.flights.dtos.FlightRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class FlightControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
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

    private final String BASE_URL = "/api/v1/private/flights";
    private Airport departure;
    private Airport arrival;

    @BeforeEach
    void setup() {
        departure = airportRepository.save(new Airport(null, "Kyiv Boryspil", "KBP", null, null));
        arrival = airportRepository.save(new Airport(null, "Lviv Intl", "LWO", null, null));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateFlight() throws Exception {
        FlightRequest request = new FlightRequest(
                "KBP",
                "LWO",
                LocalDateTime.now().plusDays(1),
                150.00,
                FlightStatus.AVAILABLE,
                50,
                100
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departureAirportCode").value("KBP"))
                .andExpect(jsonPath("$.arrivalAirportCode").value("LWO"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.availableSeats").value(50));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetFlightById() throws Exception {
        Flight flight = flightRepository.save(Flight.builder()
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .dateFlight(LocalDateTime.now().plusDays(2))
                .price(200.0)
                .status(FlightStatus.AVAILABLE)
                .availableSeats(100)
                .totalSeats(100)
                .build());

        mockMvc.perform(get(BASE_URL + "/" + flight.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departureAirportCode").value("KBP"))
                .andExpect(jsonPath("$.arrivalAirportCode").value("LWO"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateFlight() throws Exception {
        Flight flight = flightRepository.save(Flight.builder()
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .dateFlight(LocalDateTime.now().plusDays(2))
                .price(100)
                .status(FlightStatus.AVAILABLE)
                .availableSeats(80)
                .totalSeats(100)
                .build());

        FlightRequest update = new FlightRequest(
                "KBP",
                "LWO",
                LocalDateTime.now().plusDays(3),
                180.0,
                FlightStatus.AVAILABLE,
                70,
                100
        );

        mockMvc.perform(put(BASE_URL + "/" + flight.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(180.0))
                .andExpect(jsonPath("$.availableSeats").value(70));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteFlight() throws Exception {
        Flight flight = flightRepository.save(Flight.builder()
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .dateFlight(LocalDateTime.now().plusDays(1))
                .price(99.0)
                .status(FlightStatus.AVAILABLE)
                .availableSeats(10)
                .totalSeats(10)
                .build());

        mockMvc.perform(delete(BASE_URL + "/" + flight.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllFlights() throws Exception {
        flightRepository.save(Flight.builder()
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .dateFlight(LocalDateTime.now().plusDays(1))
                .price(120)
                .status(FlightStatus.AVAILABLE)
                .availableSeats(50)
                .totalSeats(100)
                .build());

        flightRepository.save(Flight.builder()
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .dateFlight(LocalDateTime.now().plusDays(2))
                .price(220)
                .status(FlightStatus.AVAILABLE)
                .availableSeats(80)
                .totalSeats(100)
                .build());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}