package com.sheoanna.airline.users.dtos;

import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.users.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        Set<String> roles = user.getRoles() != null
                ? user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    public User toEntity(UserRequest request) {
        return User.builder()
                .username(request.username())
                .password(request.password())
                .build();
    }
}