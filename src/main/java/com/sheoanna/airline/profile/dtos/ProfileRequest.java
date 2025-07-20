package com.sheoanna.airline.profile.dtos;

import org.springframework.web.multipart.MultipartFile;

public record ProfileRequest(String email,
                             String phoneNumber,
                             String address,
                             MultipartFile image) {}