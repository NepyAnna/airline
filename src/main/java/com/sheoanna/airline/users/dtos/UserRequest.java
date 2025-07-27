package com.sheoanna.airline.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank
                          String username,
                          String password) {
}
