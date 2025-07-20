package com.sheoanna.airline.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-endpoint}/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;

   /* @GetMapping("")
    public List<Profile> index() {
        List<Profile> profiles = service.findAll();
        return profiles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto newProfileData) {
        ProfileDto profile = service.store(newProfileData);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<Map<String, String>> uploadPhoto(@PathVariable Long id,
            @RequestParam("photoUrl") String photoUrl) {
        ProfileDto updatedProfile = service.uploadPhoto(id, photoUrl);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Photo updated successfully");
        response.put("photoUrl", updatedProfile.photoUrl());
        return ResponseEntity.ok(response);
    }*/
}
