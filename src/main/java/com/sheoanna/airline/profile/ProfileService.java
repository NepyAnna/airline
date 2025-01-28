package com.sheoanna.airline.profile;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sheoanna.airline.profile.exceptions.ProfileNotFoundException;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile findById(Long id)  {
        Profile profile = repository.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));
        return profile;
    }

    public void uploadPhoto(Long id, MultipartFile file) throws IOException {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        byte[] photoBytes = file.getBytes();
        profile.setPhoto(photoBytes);

        repository.save(profile);
    }

    public byte[] getPhoto(Long id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        if (profile.getPhoto() == null) {
            throw new RuntimeException("Photo not found");
        }

        return profile.getPhoto();
    }

}
