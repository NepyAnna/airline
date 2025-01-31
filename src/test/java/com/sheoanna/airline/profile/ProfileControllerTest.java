package com.sheoanna.airline.profile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserIdDto;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    private Profile profile;
    private ProfileDto profileDto;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "username", "password");
        profile = new Profile("email@test.com", "123456789", "address", null, user);
        profileDto = new ProfileDto("email@test.com", "123456789", "address", "http://newphoto.com/image.jpg",
                new UserIdDto(user.getIdUser()));
    }

    @Test
    void testFindAllProfiles() throws Exception {
        when(profileService.findAll()).thenReturn(List.of(profile));

        mockMvc.perform(get("/api/v1/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("email@test.com"));
    }

    @Test
    void testFindProfileById() throws Exception {
        when(profileService.findById(1L)).thenReturn(profile);

        mockMvc.perform(get("/api/v1/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    void testUploadPhoto() throws Exception {
        String newPhotoUrl = "http://newphoto.com/image.jpg";
        Map<String, String> response = Map.of("message", "Photo updated successfully", "photoUrl", newPhotoUrl);

        when(profileService.uploadPhoto(1L, newPhotoUrl)).thenReturn(
                new ProfileDto("email@test.com", "123456789", "address", newPhotoUrl, new UserIdDto(1L)));

        mockMvc.perform(patch("/api/v1/profiles/1/photo")
                .param("photoUrl", newPhotoUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Photo updated successfully"))
                .andExpect(jsonPath("$.photoUrl").value(newPhotoUrl));
    }

    @Test
    void testCreateProfile_Success() throws Exception {
        when(profileService.store(any(ProfileDto.class))).thenReturn(profileDto);

        String profileJson = objectMapper.writeValueAsString(profileDto);

        mockMvc.perform(post("/api/v1/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(profileJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.address").value("address"));
        verify(profileService).store(any(ProfileDto.class));
    }

    @Test
    void testDeleteProfile_Success() throws Exception {
        Long profileId = 1L;

        when(profileService.findById(profileId)).thenReturn(profile);

        mockMvc.perform(delete("/api/v1/profiles/{id}", profileId))
                .andExpect(status().isNoContent());

        verify(profileService).deleteById(profileId);
    }

}
