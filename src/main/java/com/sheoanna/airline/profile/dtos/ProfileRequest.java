package com.sheoanna.airline.profile.dtos;

public record ProfileRequest(String email,
                             String phoneNumber,
                             String address) {}