package com.sheoanna.airline.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sheoanna.airline.cloudinary.CloudinaryService;
import com.sheoanna.airline.cloudinary.UploadResult;
import com.sheoanna.airline.profile.dtos.ProfileMapper;
import com.sheoanna.airline.profile.dtos.ProfileRequest;
import com.sheoanna.airline.profile.dtos.ProfileResponse;
import com.sheoanna.airline.profile.exceptions.ProfileAlreadyExistsException;
import com.sheoanna.airline.profile.exceptions.ProfileNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final CloudinaryService cloudinaryService;

    public Page<ProfileResponse> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(profileMapper::toResponse);
    }

    public ProfileResponse findById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        if (!userService.hasAccessToProfile(profile)) {
            throw new AccessDeniedException("You are not allowed to access this profile.");
        }
        return profileMapper.toResponse(profile);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProfileResponse store(ProfileRequest newProfileData) {
        User user = userService.getAuthenticatedUser();

        if (profileRepository.findByUserId(user.getId()).isPresent()) {
            throw new ProfileAlreadyExistsException(
                    "Profile with user_id: " + user.getId() + "already exists!");
        }
        Profile profile = profileMapper.toEntity(newProfileData);
        profile.setUser(user);

        handlePhotoUpload(newProfileData, profile);

        Profile savedProfile = profileRepository.save(profile);

        return profileMapper.toResponse(profile);
    }

    @Transactional//(isolation = Isolation.READ_COMMITTED)
    public ProfileResponse update(ProfileRequest newProfileData) {
        User user = userService.getAuthenticatedUser();
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        profile.setEmail(newProfileData.email());
        profile.setPhoneNumber(newProfileData.phoneNumber());
        profile.setAddress(newProfileData.address());

        MultipartFile image = newProfileData.image();
        if (image != null && !image.isEmpty()) {
            if (profile.getPhotoPublicId() != null) {
                cloudinaryService.delete(profile.getPhotoPublicId());
            }
            UploadResult result = cloudinaryService.upload(image, "profiles");
            profile.setPhotoUrl(result.url());
            profile.setPhotoPublicId(result.publicId());
        }
        profileRepository.save(profile);
        return profileMapper.toResponse(profile);
    }

    @Transactional
    public void deleteById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        if (!userService.hasAccessToProfile(profile)) {
            throw new AccessDeniedException("You are not allowed to access this profile.");
        }
        User user = profile.getUser();

        if (user != null) {
            user.setProfile(null);
        }
        if (profile.getPhotoPublicId() != null) {
            cloudinaryService.delete(profile.getPhotoPublicId());
        }
        profileRepository.deleteById(id);

        log.info("Profile found: {}", profile.getId());
        log.info("Attempting to delete profile");
    }

    private void handlePhotoUpload(ProfileRequest newProfileData, Profile profile) {
        MultipartFile image = newProfileData.image();
        UploadResult result;

        if (image != null && !image.isEmpty()) {
            result = cloudinaryService.upload(image, "profiles");
        } else {
            result = cloudinaryService.uploadDefault("profiles");
        }
        profile.setPhotoUrl(result.url());
        profile.setPhotoPublicId(result.publicId());
    }
}
