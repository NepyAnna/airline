package com.sheoanna.airline.global;

import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.profile.exceptions.ProfileAlreadyExistsException;
import com.sheoanna.airline.users.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleUserAlreadyExists_ShouldReturnConflictResponse() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        ResponseEntity<Map<String, String>> response = handler.handleUserAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", response.getBody().get("error"));
    }

    @Test
    void handleAirportAlreadyExists_ShouldReturnConflictResponse() {
        AirportAlreadyExistsException exception = new AirportAlreadyExistsException("Airport with IATA code Airport already exists already exists");

        ResponseEntity<Map<String, String>> response = handler.handleAirportAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleProfileAlreadyExists_ShouldReturnConflictResponse() {
        ProfileAlreadyExistsException exception = new ProfileAlreadyExistsException("Profile already exists");

        ResponseEntity<Map<String, String>> response = handler.handleProfileAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Profile already exists", response.getBody().get("error"));
    }
}