package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final CloudinaryService cloudinaryService;
    public ImageController(CloudinaryService svc) { this.cloudinaryService = svc; }

    @PostMapping("/upload")
    public ResponseEntity<Map<String,String>> uploadImage(
            @RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.upload(file);
            return ResponseEntity.ok(Collections.singletonMap("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Upload thất bại"));
        }
    }
}

