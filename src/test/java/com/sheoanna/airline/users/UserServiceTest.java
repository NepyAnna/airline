package com.sheoanna.airline.users;

import com.sheoanna.airline.profile.Profile;
import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.users.dtos.UserMapper;
import com.sheoanna.airline.users.dtos.UserResponse;
import com.sheoanna.airline.users.exceptions.UserNotFoundException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserService userService;

    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;

    private User user;
    private UserResponse response;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .username("test")
                .roles(Set.of(new Role(1L, "USER", new HashSet<>())))
                .build();

        response = UserResponse.builder()
                .id(1L)
                .username("test")
                .roles(Set.of("USER"))
                .profile(null)
                .build();
    }

    private void mockAuthenticationWithUsername(String username) {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
    }

    private User buildUser(Long id, String username, String roleName) {
        Role role = new Role();
        role.setName(roleName);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setRoles(Set.of(role));
        return user;
    }

    @Test
    void getAuthenticatedUser_shouldReturnUser_whenExists() {
        String username = "john_doe";
        mockAuthenticationWithUsername(username);

        User expectedUser = User.builder().id(1L).username(username).build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User result = userService.getAuthenticatedUser();

        assertEquals(expectedUser, result);
    }

    @Test
    void getAuthenticatedUser_shouldThrow_whenUserNotFound() {
        String username = "unknown";
        mockAuthenticationWithUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getAuthenticatedUser());
    }

    @Test
    void hasAccessToProfile_shouldReturnTrue_ifAdmin() {
        mockAuthenticationWithUsername("admin");

        User admin = buildUser(1L, "admin", "ROLE_ADMIN");

        Profile profile = new Profile();
        profile.setUser(new User(2L, null, null, null, null, null));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(admin));

        assertTrue(userService.hasAccessToProfile(profile));
    }

    @Test
    void hasAccessToProfile_shouldReturnTrue_ifOwner() {
        mockAuthenticationWithUsername("owner");

        User owner = buildUser(1L, "owner", "USER");

        Profile profile = new Profile();
        profile.setUser(owner);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(owner));

        assertTrue(userService.hasAccessToProfile(profile));
    }

    @Test
    void hasAccessToProfile_shouldReturnFalse_ifNotAdminOrOwner() {
        mockAuthenticationWithUsername("other");

        User other = buildUser(2L, "other", "USER");

        User profileOwner = buildUser(1L, "owner", "USER");
        Profile profile = new Profile();
        profile.setUser(profileOwner);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(other));

        assertFalse(userService.hasAccessToProfile(profile));
    }

    @Test
    void findAll_shouldReturnPageOfUserResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toResponse(user)).thenReturn(response);

        Page<UserResponse> result = userService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("test", result.getContent().get(0).username());
    }

    @Test
    void findById_shouldReturnUserResponse_whenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.findById(1L);

        assertEquals(response, result);
    }

    @Test
    void findById_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(999L));
    }

    @Test
    void deleteById_shouldDelete_whenUserExists() {
        User userToDelete = User.builder().id(1L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));

        userService.deleteById(1L);

        verify(userRepository, times(1)).delete(userToDelete);
    }

    @Test
    void deleteById_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(2L));
    }
}