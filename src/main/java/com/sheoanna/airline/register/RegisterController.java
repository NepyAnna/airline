package com.sheoanna.airline.register;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheoanna.airline.users.UserDto;

@RestController
@RequestMapping(path = "${api-endpoint}/register")
public class RegisterController {

    private final RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto newUser) {

        Map<String, String> response = service.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
