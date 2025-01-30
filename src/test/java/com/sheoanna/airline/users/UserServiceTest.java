package com.sheoanna.airline.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheoanna.airline.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser.setIdUser(1L);
    }
    
    @Test
    void findAll_ShouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        List<User> users = userService.findAll();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
    }
    
    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        User user = userService.findById(1L);
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }
    
    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(2L));
    }
    
    @Test
    void deleteById_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);
        assertDoesNotThrow(() -> userService.deleteById(1L));
        verify(userRepository, times(1)).delete(testUser);
    }
}

