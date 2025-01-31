package com.sheoanna.airline.profile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheoanna.airline.users.User;

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
    
    @BeforeEach
    void setUp() {
        User user = new User(1L, "username", "password");
        profile = new Profile("email@test.com", "123456789", "address", null, user);
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
}
