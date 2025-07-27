package com.sheoanna.airline.users.exceptions;

import com.sheoanna.airline.global.AppException;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
