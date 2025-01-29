package com.sheoanna.airline.profile;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api-endpoint}/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("")
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

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> showPhoto(@PathVariable Long id) {
        byte[] photo = service.getPhoto(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // або MediaType.IMAGE_PNG
                .body(photo);
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile file)
            throws IOException {
        service.uploadPhoto(id, file);
        return ResponseEntity.ok("Photo uploaded successfully!");
    }

}
