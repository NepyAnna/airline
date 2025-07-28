package com.sheoanna.airline.role.exceptions;

import com.sheoanna.airline.global.AppException;

public class RoleNotFoundException extends AppException {
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}