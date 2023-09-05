package com.greenrock.backendgreenrock.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import jakarta.annotation.Resource;

// import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/{imageName}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String imageName) {
        try {
            // Ganti "uploads" dengan path yang sesuai di mana gambar Anda disimpan.
            Path imagePath = Paths.get("uploads", imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/bukti/{imageName}")
    public ResponseEntity<UrlResource> getFoto(@PathVariable String imageName) {
        try {
            // Ganti "uploads" dengan path yang sesuai di mana gambar Anda disimpan.
            Path imagePath = Paths.get("uploads", imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
