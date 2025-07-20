package com.sheoanna.airline.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sheoanna.airline.users.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;
    private final UserRepository userRepository;

/*

    public List<Profile> findAll() {
        List<Profile> profiles = repository.findAll();
        return profiles;
    }

    public Profile findById(Long id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));
        return profile;
    }

    @Transactional
    public ProfileDto store(ProfileDto newProfileData) {
        User user = userRepository.findById(newProfileData.user().userId())
                .orElseThrow(
                        () -> new UserNotFoundException("User not found with id: " + newProfileData.user().userId()));

        Profile profile = new Profile(newProfileData.email(), newProfileData.phoneNumber(), newProfileData.address(),
                null, user);

        if (repository.findByUserId(newProfileData.user().userId()).isPresent()) {
            throw new ProfileAlreadyExistsException(
                    "Profile with user_id: " + newProfileData.user().userId() + "already exists!");
        }
        Profile savedProfile = repository.save(profile);

        return new ProfileDto(savedProfile.getEmail(), savedProfile.getPhoneNumber(), savedProfile.getAddress(),
                savedProfile.getPhotoUrl(),
                new UserIdDto(savedProfile.getUser().getIdUser()));
    }

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
