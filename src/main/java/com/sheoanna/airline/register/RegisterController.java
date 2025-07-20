package com.sheoanna.airline.register;

import java.util.Map;

import com.sheoanna.airline.users.dtos.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService service;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequest newUser) {

        Map<String, String> response = service.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
