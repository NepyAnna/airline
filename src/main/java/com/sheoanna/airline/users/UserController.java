package com.sheoanna.airline.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("${api-endpoint}/users")
public class UserController {
    private UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("")
    public List<User> index() {
        List<User> users = service.findAll();
                return users;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    

}
