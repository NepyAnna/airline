package com.sheoanna.airline.profile;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sheoanna.airline.profile.exceptions.ProfileNotFoundException;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // Завантаження фото
    public void uploadPhoto(Long id, MultipartFile file) throws IOException {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        // Конвертуємо файл у масив байтів
        byte[] photoBytes = file.getBytes();
        profile.setPhoto(photoBytes);

        // Зберігаємо в базу
        profileRepository.save(profile);
    }

    // Отримання фото
    public byte[] getPhoto(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        if (profile.getPhoto() == null) {
            throw new RuntimeException("Photo not found");
        }

        return profile.getPhoto();
    }

    /*// Отримання DTO з динамічним URL
    public ProfileDto getProfileDto(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        String photoUrl = "/api/profiles/" + id + "/photo"; // Генерація URL
        return new ProfileDto( photoUrl);
    }*/


}
