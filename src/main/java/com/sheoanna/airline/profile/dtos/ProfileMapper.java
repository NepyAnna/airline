package com.sheoanna.airline.profile.dtos;

import com.sheoanna.airline.profile.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileResponse toResponse(Profile profile);
    Profile toEntity(ProfileRequest request);
}
