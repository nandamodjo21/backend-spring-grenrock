// package com.greenrock.backendgreenrock.controller;

// // import com.greenrock.backendgreenrock.dao.SewaDao;
// import com.greenrock.backendgreenrock.entity.Penyewa;
// import com.greenrock.backendgreenrock.service.SewaService;
// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api")
// @Transactional
// @RequiredArgsConstructor
// public class Sewa {

// private final SewaService sewaService;

// @PostMapping("/pesan")
// public ResponseEntity<Penyewa> simpan(@RequestBody Penyewa penyewa) {
// return new ResponseEntity<>(sewaService.saveSewa(penyewa),
// HttpStatus.CREATED);
// }

// @GetMapping("/lihat")
// public ResponseEntity<List<Penyewa>> getAll() {
// return new ResponseEntity<>(sewaService.findSewa(), HttpStatus.OK);
// }

// }
