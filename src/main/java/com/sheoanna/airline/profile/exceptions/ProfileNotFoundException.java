package com.sheoanna.airline.profile.exceptions;

import com.sheoanna.airline.global.AppException;

public class ProfileNotFoundException extends AppException {
    public ProfileNotFoundException(Long id) {
        super("Profile with ID: " + id +  "not found.");
    }
    public ProfileNotFoundException(String message) {
        super(message);
    }
    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
