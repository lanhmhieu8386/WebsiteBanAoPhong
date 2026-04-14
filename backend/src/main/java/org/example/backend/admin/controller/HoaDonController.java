package org.example.backend.admin.controller;

import org.example.backend.admin.service.HoaDonService;
import org.example.backend.common.dto.response.HoaDonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/don-hang")
    public ResponseEntity<List<HoaDonResponse>> getDanhSachDonHang() {
        return ResponseEntity.ok(hoaDonService.getDanhSachDonHang());
    }

    @GetMapping("/hoa-don")
    public ResponseEntity<List<HoaDonResponse>> getDanhSachHoaDon() {
        return ResponseEntity.ok(hoaDonService.getDanhSachHoaDon());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<HoaDonResponse> getDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(hoaDonService.getDetail(id));
    }

    @PutMapping("/don-hang/{id}/trang-thai")
    public ResponseEntity<HoaDonResponse> capNhatTrangThaiDonHang(
            @PathVariable Integer id,
            @RequestParam Integer trangThaiMoi,
            @RequestParam(defaultValue = "Admin") String nguoiCapNhat
    ) {
        return ResponseEntity.ok(
                hoaDonService.capNhatTrangThaiDonHang(id, trangThaiMoi, nguoiCapNhat)
        );
    }

    @PutMapping("/hoa-don/{id}/trang-thai")
    public ResponseEntity<HoaDonResponse> capNhatTrangThaiHoaDon(
            @PathVariable Integer id,
            @RequestParam Integer trangThaiMoi,
            @RequestParam(defaultValue = "Admin") String nguoiCapNhat
    ) {
        return ResponseEntity.ok(
                hoaDonService.capNhatTrangThaiHoaDon(id, trangThaiMoi, nguoiCapNhat)
        );
    }
}