package com.sheoanna.airline.profile;

import com.sheoanna.airline.users.UserIdDto;

public record ProfileDto(String email, String phoneNumber, String address, UserIdDto user) {}
