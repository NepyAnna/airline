package com.sheoanna.airline.profile;

import com.sheoanna.airline.cloudinary.CloudinaryService;
import com.sheoanna.airline.cloudinary.UploadResult;
import com.sheoanna.airline.profile.dtos.ProfileMapper;
import com.sheoanna.airline.profile.dtos.ProfileRequest;
import com.sheoanna.airline.profile.dtos.ProfileResponse;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private UploadResult uploadResult;

    private User user;
    private Profile profile;
    private ProfileRequest request;
    private ProfileResponse response;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("testUser").build();
        profile = Profile.builder().id(1L).email("test@email.com").user(user).build();
        request = new ProfileRequest("test@email.com", "123", "Address", multipartFile);
        response = new ProfileResponse(1L, "test@email.com", "123", "Address", "url", 1L);
    }

    @Test
    void findAll_ReturnsPageOfProfiles() {
        Page<Profile> profiles = new PageImpl<>(List.of(profile));
        when(profileRepository.findAll(any(Pageable.class))).thenReturn(profiles);
        when(profileMapper.toResponse(profile)).thenReturn(response);

        Page<ProfileResponse> result = profileService.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        verify(profileMapper).toResponse(profile);
    }

    @Test
    void findById_WithAccess_ReturnsProfile() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(userService.hasAccessToProfile(profile)).thenReturn(true);
        when(profileMapper.toResponse(profile)).thenReturn(response);

        ProfileResponse result = profileService.findById(1L);

        assertEquals(response, result);
    }

    @Test
    void findById_WithoutAccess_ThrowsAccessDenied() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(userService.hasAccessToProfile(profile)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> profileService.findById(1L));
    }

    @Test
    void store_CreatesProfileSuccessfully() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(profileMapper.toEntity(request)).thenReturn(profile);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(cloudinaryService.upload(multipartFile, "profiles")).thenReturn(
                new UploadResult("photo_url", "photo_id"));
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toResponse(profile)).thenReturn(response);

        ProfileResponse result = profileService.store(request);

        assertEquals("test@email.com", result.email());
        verify(profileRepository).save(any(Profile.class));
    }

    @Test
    void update_UpdatesProfileSuccessfully() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));
        when(multipartFile.isEmpty()).thenReturn(false);
        when(cloudinaryService.upload(any(), any())).thenReturn(new UploadResult("url", "publicId"));
        when(profileMapper.toResponse(profile)).thenReturn(response);

        ProfileResponse updated = profileService.update(request);

        assertEquals("test@email.com", updated.email());
        verify(cloudinaryService).upload(multipartFile, "profiles");
    }

    @Test
    void deleteById_WithAccess_DeletesProfile() {
        user.setProfile(profile);
        profile.setUser(user);
        profile.setPhotoPublicId("someId");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(userService.hasAccessToProfile(profile)).thenReturn(true);

        profileService.deleteById(1L);

        verify(cloudinaryService).delete("someId");
        verify(profileRepository).deleteById(1L);
    }

    @Test
    void deleteById_WithoutAccess_ThrowsAccessDenied() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(userService.hasAccessToProfile(profile)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> profileService.deleteById(1L));
    }
}