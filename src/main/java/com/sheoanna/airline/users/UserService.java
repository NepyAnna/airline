package com.sheoanna.airline.users;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sheoanna.airline.users.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

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
