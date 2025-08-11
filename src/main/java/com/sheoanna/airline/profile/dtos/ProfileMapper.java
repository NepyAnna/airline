package com.sheoanna.airline.profile.dtos;

import com.sheoanna.airline.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "email", target = "email")
    ProfileResponse toResponse(Profile profile);

    @Mapping(source = "email", target = "email")
    Profile toEntity(ProfileRequest request);
}
