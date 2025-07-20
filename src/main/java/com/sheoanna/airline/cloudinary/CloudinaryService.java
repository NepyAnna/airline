package com.sheoanna.airline.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    @Value("${DEFAULT_PROFILE_IMAGE}")
    private String defaultProfileImageUrl;
    private final Cloudinary cloudinary;

    public UploadResult upload(MultipartFile file, String folder) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folder);

            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);

            return new UploadResult(
                    result.get("secure_url").toString(),
                    result.get("public_id").toString()
            );

        } catch (IOException | RuntimeException e) {
            return new UploadResult(
                    defaultProfileImageUrl,
                    null
            );
        }
    }
}
