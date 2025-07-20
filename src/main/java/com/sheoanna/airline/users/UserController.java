package com.sheoanna.airline.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api-endpoint}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

  /*  @GetMapping("")
    public List<User> index() {
        List<User> users = service.findAll();
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }*/
}
