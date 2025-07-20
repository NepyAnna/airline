package com.sheoanna.airline.users.dtos;

import com.sheoanna.airline.profile.dtos.ProfileResponse;

import java.util.Set;

public record UserResponse(Long id,
                           String username,
                           Set<String> roles,
                           ProfileResponse profile) {}
