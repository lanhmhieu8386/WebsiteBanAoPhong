package org.example.backend.admin.controller;

import org.example.backend.admin.service.NhanVienService;
import org.example.backend.admin.service.TaiKhoanService;
import org.example.backend.common.dto.request.NhanVienRequest;
import org.example.backend.common.dto.request.TaiKhoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/nhan-vien")
public class NhanVienController {
    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private TaiKhoanService  taiKhoanService;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Boolean trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(nhanVienService.getAll(keyword, trangThai, page, size));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getById(id));
    }

    @PostMapping("/them")
    public ResponseEntity<?> add(@RequestBody NhanVienRequest request) {
        return ResponseEntity.ok(nhanVienService.add(request));
    }

    @PutMapping("/sua/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody NhanVienRequest request) {
        return ResponseEntity.ok(nhanVienService.update(id, request));
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        nhanVienService.delete(id);
        return ResponseEntity.ok("Xóa nhân viên thành công");
    }

}
