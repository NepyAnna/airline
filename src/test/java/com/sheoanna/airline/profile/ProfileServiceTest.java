package com.sheoanna.airline.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheoanna.airline.users.User;
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
        profile = new Profile("email@test.com", "123456789", "address", null, null, user);
    }

    @Test
    void testFindProfileById() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        
        Profile foundProfile = profileService.findById(1L);
        
        assertEquals("email@test.com", foundProfile.getEmail());
    }
}
