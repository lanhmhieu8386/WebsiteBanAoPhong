package org.example.backend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.LichSuDonHangService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/lich-su-don-hang")
@RequiredArgsConstructor
public class LichSuDonHangController {

    private final LichSuDonHangService lichSuDonHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(lichSuDonHangService.getAll());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable Integer id) {
        return ResponseEntity.ok(lichSuDonHangService.getDetailById(id));
    }

    @GetMapping("/hoa-don/{idHoaDon}")
    public ResponseEntity<?> getByHoaDonId(@PathVariable Integer idHoaDon) {
        return ResponseEntity.ok(lichSuDonHangService.getByHoaDonId(idHoaDon));
    }
}