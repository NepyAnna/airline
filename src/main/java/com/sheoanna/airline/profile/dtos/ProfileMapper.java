package com.sheoanna.airline.profile.dtos;

import com.sheoanna.airline.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "user.id", target = "userId")
    ProfileResponse toResponse(Profile profile);
    Profile toEntity(ProfileRequest request);
}
