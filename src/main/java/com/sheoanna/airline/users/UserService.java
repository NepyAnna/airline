package com.sheoanna.airline.users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sheoanna.airline.users.exceptions.UserNotFoundException;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return user;
    }

}
