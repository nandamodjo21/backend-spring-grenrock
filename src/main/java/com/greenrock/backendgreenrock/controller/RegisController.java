package com.greenrock.backendgreenrock.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// import java.sql.Timestamp;
import java.util.Formatter;
import java.util.UUID;

// import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenrock.backendgreenrock.dao.RoleDao;
import com.greenrock.backendgreenrock.dao.UserDao;
import com.greenrock.backendgreenrock.entity.Role;
import com.greenrock.backendgreenrock.entity.User;

// import jakarta.persistence.criteria.Path;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class RegisController {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("nama") String nama,
            @RequestParam("email") String email,
            @RequestParam("noHp") String noHp,
            @RequestParam("alamat") String alamat,
            @RequestParam("password") String password,
            @RequestParam("nik") String nik,
            @RequestParam("file") MultipartFile file) {

        try {
            // Simpan gambar ke sistem file
            String imagePath = saveImage(file);

            String encryptedPassword = encryptPassword(password);
            // Buat objek User baru
            User user = new User();
            user.setNama(nama);
            user.setEmail(email);
            user.setNoHp(noHp);
            user.setAlamat(alamat);
            user.setPassword(encryptedPassword);
            user.setNik(nik);
            user.setImagePath(imagePath);

            Role userRole = roleDao.findById(2).orElse(null); // Misalnya, peran dengan ID 2
            user.setRole(userRole);
            // Simpan user ke database
            userDao.save(user);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 200);
            jsonObject.put("message", "sukses registrasi");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toJSONString());
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 404);
            jsonObject.put("message", "gagal");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonObject.toJSONString());
        }
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(password.getBytes());

        byte[] digest = md.digest();
        try (Formatter formatter = new Formatter()) {
            for (byte b : digest) {
                formatter.format("%02x", b);
            }

            return formatter.toString();
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        String uploadDir = "uploads"; // Direktori penyimpanan gambar

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
