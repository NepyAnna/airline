package com.sheoanna.airline.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheoanna.airline.profile.exceptions.ProfileNotFoundException;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserIdDto;
import com.sheoanna.airline.users.UserRepository;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private ProfileService profileService;
    
    private Profile profile;
    
    @BeforeEach
    void setUp() {
        User user = new User(1L, "username", "password");
        profile = new Profile("email@test.com", "123456789", "address", null, user);
    }

    @Test
    void testFindProfileById() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        
        Profile foundProfile = profileService.findById(1L);
        
        assertEquals("email@test.com", foundProfile.getEmail());
    }

    @Test
    void testUploadPhoto_Success() {
        Long profileId = 1L;
        String newPhotoUrl = "http://newphoto.com/image.jpg";

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        ProfileDto updatedProfile = profileService.uploadPhoto(profileId, newPhotoUrl);

        assertEquals(newPhotoUrl, updatedProfile.photoUrl());
        verify(profileRepository).save(profile);
    }

    @Test
    void testUploadPhoto_ProfileNotFound() {
        Long profileId = 1L;
        String newPhotoUrl = "http://newphoto.com/image.jpg";

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(ProfileNotFoundException.class, () -> {
            profileService.uploadPhoto(profileId, newPhotoUrl);
        });

        assertEquals("Profile not found with id: " + profileId, exception.getMessage());
        verify(profileRepository, never()).save(any(Profile.class));
    }

    @Test
    void testStoreProfile_Success() {
        User user = new User(1L, "username", "password");
        ProfileDto newProfileData = new ProfileDto("newemail@test.com", "987654321", "new address", "https://postimg.cc/Y4hcfndB",new UserIdDto(user.getIdUser()));
        
        when(userRepository.findById(user.getIdUser())).thenReturn(Optional.of(user));
        when(profileRepository.findByUserId(user.getIdUser())).thenReturn(Optional.empty());
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> {
            Profile savedProfile = invocation.getArgument(0);
            savedProfile.setIdProfile(1L);
            return savedProfile;
        });

        ProfileDto savedProfile = profileService.store(newProfileData);

        assertEquals(newProfileData.email(), savedProfile.email());
        assertEquals(newProfileData.phoneNumber(), savedProfile.phoneNumber());
        assertEquals(newProfileData.address(), savedProfile.address());
        assertEquals(user.getIdUser(), savedProfile.user().userId());

        verify(userRepository).findById(user.getIdUser());
        verify(profileRepository).findByUserId(user.getIdUser());
        verify(profileRepository).save(any(Profile.class));
    }

    @Test
    void testDeleteProfile_Success() {
        Long profileId = 1L;

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        profileService.deleteById(profileId);

        verify(profileRepository).delete(profile);
    }

    @Test
    void testDeleteProfile_ProfileNotFound() {
        Long profileId = 1L;

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProfileNotFoundException.class, () -> {
            profileService.deleteById(profileId);
        });

        assertEquals("Profile not found with id: " + profileId, exception.getMessage());

        verify(profileRepository, never()).delete(any(Profile.class));
    }


}
