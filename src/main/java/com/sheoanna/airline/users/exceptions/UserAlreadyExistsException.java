package com.sheoanna.airline.users.exceptions;

public class UserAlreadyExistsException extends UserException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
