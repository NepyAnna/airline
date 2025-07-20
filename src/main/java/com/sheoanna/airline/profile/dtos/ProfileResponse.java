package com.sheoanna.airline.profile.dtos;

public record ProfileResponse(Long id,
                              String email,
                              String phoneNumber,
                              String address,
                              String photoUrl,
                              Long userId) {}