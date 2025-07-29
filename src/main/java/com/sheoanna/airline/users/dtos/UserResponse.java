package com.sheoanna.airline.users.dtos;

import com.sheoanna.airline.profile.dtos.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Builder
public record UserResponse(Long id,
                           String username,
                           Set<String> roles,
                           ProfileResponse profile) {}
