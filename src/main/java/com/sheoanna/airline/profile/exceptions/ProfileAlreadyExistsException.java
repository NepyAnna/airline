package com.sheoanna.airline.profile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Profile alredy exist")
public class ProfileAlreadyExistsException extends ProfileException{
    public ProfileAlreadyExistsException(String message) {
        super(message);
    }

    public ProfileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
