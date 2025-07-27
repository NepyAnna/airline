package com.sheoanna.airline.profile.exceptions;

import com.sheoanna.airline.global.AppException;

public class ProfileAlreadyExistsException extends AppException {
    public ProfileAlreadyExistsException(Long id) {
        super("Profile with ID " + id + " already exists!");
    }
    public ProfileAlreadyExistsException(String message) {
        super(message);
    }
    public ProfileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
