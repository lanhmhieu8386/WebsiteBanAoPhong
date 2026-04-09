package org.example.backend.customer.controller;

import org.example.backend.customer.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/san-pham")
public class SanPhamCustomerController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/hien-thi")
    private ResponseEntity<?> hienThi() {
        return ResponseEntity.ok(sanPhamService.findAll());
    }

    @GetMapping("/by-danh-muc/{id}")
    private ResponseEntity<?> byDanhMuc(@PathVariable Long id) {
        return ResponseEntity.ok(sanPhamService.findByIdDanhMuc(id));
    }

    @GetMapping("/detail/{id}")
    private ResponseEntity<?> detail(@PathVariable Long id) {
        return ResponseEntity.ok(sanPhamService.findById(id));
    }

}
