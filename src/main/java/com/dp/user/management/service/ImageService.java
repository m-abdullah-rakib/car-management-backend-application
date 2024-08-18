package com.dp.user.management.service;

import com.dp.user.management.dto.response.ImageUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "/home/rakib/Desktop/DriveImage/";

    public ResponseEntity<?> saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file uploaded.");
        }

        // Generate a unique 8-digit random number
        String randomNumber = String.format("%08d", new Random().nextInt(100000000));

        // Define the new image name
        String newImageName = "image-" + randomNumber + ".jpg";

        // Image upload path
        Path uploadPath = Paths.get(UPLOAD_DIR + newImageName);

        try {
            Files.write(uploadPath, image.getBytes());
            return ResponseEntity.ok(new ImageUploadResponse(newImageName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
