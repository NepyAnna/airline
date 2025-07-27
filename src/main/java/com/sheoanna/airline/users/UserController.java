package com.sheoanna.airline.users;

import com.sheoanna.airline.users.dtos.UserMapper;
import com.sheoanna.airline.users.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api-endpoint}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper userMapper;

    @GetMapping("")
    public Page<UserResponse> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
