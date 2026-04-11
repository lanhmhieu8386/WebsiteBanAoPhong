package org.example.backend.customer.controller;

import org.example.backend.customer.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/danh-muc")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> hienThi() {
        return ResponseEntity.ok(danhMucService.findAllDanhMuc());
    }
}
