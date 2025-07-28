package com.sheoanna.airline.users.dtos;

import com.sheoanna.airline.profile.dtos.ProfileResponse;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserResponse(Long id,
                           String username,
                           Set<String> roles,
                           ProfileResponse profile) {}
