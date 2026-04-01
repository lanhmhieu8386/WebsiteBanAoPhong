package org.example.backend.admin.controller;

import org.example.backend.admin.service.KhachHangService;
import org.example.backend.admin.service.TaiKhoanService;
import org.example.backend.common.dto.request.KhachHangRequest;
import org.example.backend.common.dto.request.TaiKhoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(khachHangService.getAll(keyword, trangThai, page, size));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getById(id));
    }

    @PostMapping("/them")
    public ResponseEntity<?> add(@RequestBody KhachHangRequest request) {
        return ResponseEntity.ok(khachHangService.add(request));
    }

    @PutMapping("/sua/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody KhachHangRequest request) {
        return ResponseEntity.ok(khachHangService.update(id, request));
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        khachHangService.delete(id);
        return ResponseEntity.ok("Xóa khách hàng thành công");
    }

    // tài khoản khách hàng
    @GetMapping("/{idKhachHang}/tai-khoan")
    public ResponseEntity<?> getTaiKhoan(@PathVariable Integer idKhachHang) {
        return ResponseEntity.ok(taiKhoanService.getTaiKhoanKhachHang(idKhachHang));
    }

    @PostMapping("/{idKhachHang}/tai-khoan")
    public ResponseEntity<?> themTaiKhoan(@PathVariable Integer idKhachHang,
                                          @RequestBody TaiKhoanRequest request) {
        return ResponseEntity.ok(taiKhoanService.themTaiKhoanChoKhachHang(idKhachHang, request));
    }

    @PutMapping("/{idKhachHang}/tai-khoan")
    public ResponseEntity<?> capNhatTaiKhoan(@PathVariable Integer idKhachHang,
                                             @RequestBody TaiKhoanRequest request) {
        return ResponseEntity.ok(taiKhoanService.capNhatTaiKhoanKhachHang(idKhachHang, request));
    }

    @DeleteMapping("/{idKhachHang}/tai-khoan")
    public ResponseEntity<?> xoaTaiKhoan(@PathVariable Integer idKhachHang) {
        taiKhoanService.xoaTaiKhoanKhachHang(idKhachHang);
        return ResponseEntity.ok("Xóa tài khoản khách hàng thành công");
    }
}
