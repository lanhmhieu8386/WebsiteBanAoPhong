package org.example.backend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.HoaDonChiTietService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/hoa-don-chi-tiet")
@RequiredArgsConstructor
public class HoaDonChiTietController {

    private final HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/hoa-don/{idHoaDon}")
    public ResponseEntity<?> getDanhSachByHoaDonId(@PathVariable Integer idHoaDon) {
        return ResponseEntity.ok(hoaDonChiTietService.getDanhSachByHoaDonId(idHoaDon));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable Integer id) {
        return ResponseEntity.ok(hoaDonChiTietService.getDetailById(id));
    }
}