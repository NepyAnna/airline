package com.sheoanna.airline.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sheoanna.airline.profile.dtos.ProfileRequest;
import com.sheoanna.airline.profile.dtos.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api-endpoint}/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("")
    public Page<ProfileResponse> getProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size/*,
            @RequestParam(defaultValue = "id") String sortBy*/
    ) {
        Pageable pageable = PageRequest.of(page, size/*, Sort.by(sortBy)*/);
        return profileService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponse> createProfile(@ModelAttribute ProfileRequest request) {
        return ResponseEntity.ok(profileService.store(request));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponse> updateProfile(@ModelAttribute ProfileRequest request) {
        return ResponseEntity.ok(profileService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long id) {
        profileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
