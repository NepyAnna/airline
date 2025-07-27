package com.sheoanna.airline.users.dtos;

import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToNames")
    @Mapping(source = "profile", target = "profile")
    UserResponse toResponse(User user);

    User toEntity(UserRequest request);

    @Named("mapRolesToNames")
    default Set<String> mapRolesToNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
