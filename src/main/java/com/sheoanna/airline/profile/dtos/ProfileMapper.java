package com.sheoanna.airline.profile.dtos;

import com.sheoanna.airline.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "photoUrl", target = "photoUrl")
    ProfileResponse toResponse(Profile profile);

    Profile toEntity(ProfileRequest request);
}
