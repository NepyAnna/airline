package com.sheoanna.airline.register;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.global.GlobalExceptionHandler;
import com.sheoanna.airline.profile.exceptions.ProfileAlreadyExistsException;
import com.sheoanna.airline.users.exceptions.UserAlreadyExistsException;

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
        AirportAlreadyExistsException exception = new AirportAlreadyExistsException("Airport already exists");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleAirportAlreadyExists(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Airport already exists", response.getBody().get("error"));
    }

    @Test
    void handleProfileAlreadyExists_ShouldReturnConflictResponse() {
        // Arrange
        ProfileAlreadyExistsException exception = new ProfileAlreadyExistsException("Profile already exists");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleProfileAlreadyExists(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Profile already exists", response.getBody().get("error"));
    }
}

