package com.sheoanna.airline.profile;

import com.sheoanna.airline.cloudinary.CloudinaryService;
import com.sheoanna.airline.cloudinary.UploadResult;
import com.sheoanna.airline.profile.dtos.ProfileMapper;
import com.sheoanna.airline.profile.dtos.ProfileRequest;
import com.sheoanna.airline.profile.dtos.ProfileResponse;
import com.sheoanna.airline.profile.exceptions.ProfileAlreadyExistsException;
import com.sheoanna.airline.profile.exceptions.ProfileNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
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

        User currentUser = userService.getAuthenticatedUser();

        if (!userService.hasAccessToProfile(profile)) {
            throw new AccessDeniedException("You are not allowed to access this profile.");
        }
        return profileMapper.toResponse(profile);
    }

    @Transactional
    public ProfileResponse store(ProfileRequest newProfileData) {
        User user = userService.getAuthenticatedUser();

        if(profileRepository.findByUserId(user.getId()).isPresent()){
            throw new ProfileAlreadyExistsException(
                    "Profile with user_id: " + user.getId() + "already exists!");
        }
        Profile profile = profileMapper.toEntity(newProfileData);
        profile.setUser(user);

        if (newProfileData.image() != null && !newProfileData.image().isEmpty()) {
            UploadResult result = cloudinaryService.upload(newProfileData.image(), "profiles");
            profile.setPhotoUrl(result.url());
            profile.setPhotoPublicId(result.publicId());
        } else {
            UploadResult result = cloudinaryService.upload(null, "profiles");
            profile.setPhotoUrl(result.url());
            profile.setPhotoPublicId(result.publicId());
        }

        Profile savedProfile = profileRepository.save(profile);

        return profileMapper.toResponse(profile);
    }
/*
    @Transactional
    public void deleteById(Long id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));
        repository.delete(profile);
    }

    @Transactional
    public ProfileDto uploadPhoto(Long id, String photoUrl) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));
        profile.updatePhoto(photoUrl);
        repository.save(profile);
        return new ProfileDto(profile.getEmail(), profile.getPhoneNumber(), profile.getAddress(), profile.getPhotoUrl(),
                new UserIdDto(profile.getUser().getIdUser()));
    }
*/

}
