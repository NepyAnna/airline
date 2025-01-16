package com.sheoanna.airline.users;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

}
