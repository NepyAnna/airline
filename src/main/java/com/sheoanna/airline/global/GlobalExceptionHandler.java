package com.sheoanna.airline.global;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sheoanna.airline.airport.exceptions.AirportAlreadyExistsException;
import com.sheoanna.airline.profile.exceptions.ProfileAlreadyExistsException;
import com.sheoanna.airline.users.exceptions.UserAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AirportAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAirportAlreadyExists(AirportAlreadyExistsException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleProfileAlreadyExists(ProfileAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
