package com.greenrock.backendgreenrock.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenrock.backendgreenrock.dao.PenyewaanDao;
import com.greenrock.backendgreenrock.entity.Penyewaan;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {

    private final PenyewaanDao penyewaanDao;

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/bukti/{id}")
    public ResponseEntity<String> updateData(
            @PathVariable("id") String id,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            Optional<Penyewaan> penyewaan = penyewaanDao.findById(id);
            if (penyewaan.isPresent()) {
                Penyewaan penye = penyewaan.get();

                String imagePath = saveImage(imageFile);

                // String imageName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                penye.setImagePath(imagePath);
                penyewaanDao.save(penye);

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("code", 200);
                jsonObject.put("message", "berhasil upload");
                return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toJSONString());
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 404);
                jsonObject.put("message", "berhasil upload");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mengupdate data penyewaan: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        String uploadDir = UPLOAD_DIR;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = Paths.get(uploadDir, fileName).toString();
        Path storagePath = Paths.get(filePath);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, storagePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

}
