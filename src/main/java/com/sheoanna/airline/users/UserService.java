package com.sheoanna.airline.users;

import com.sheoanna.airline.profile.Profile;
import com.sheoanna.airline.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
    /*public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        repository.delete(user);
    }
*/}
