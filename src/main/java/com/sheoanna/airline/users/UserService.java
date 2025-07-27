package com.sheoanna.airline.users;

import com.sheoanna.airline.profile.Profile;
import com.sheoanna.airline.role.Role;
import com.sheoanna.airline.users.dtos.UserMapper;
import com.sheoanna.airline.users.dtos.UserResponse;
import com.sheoanna.airline.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println("Authenticated username: " + username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(roleName -> roleName.equalsIgnoreCase("ADMIN") || roleName.equalsIgnoreCase("ROLE_ADMIN"));
    }

    public boolean hasAccessToProfile(Profile profile) {
        User user = getAuthenticatedUser();
        return isAdmin(user) || profile.getUser().getId().equals(user.getId());
    }

    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(user);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }
}
