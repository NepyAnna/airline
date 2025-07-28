package com.sheoanna.airline.users.exceptions;

import com.sheoanna.airline.global.AppException;

public class UserAlreadyExistsException extends AppException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
