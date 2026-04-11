package org.example.backend.admin.controller;

import org.example.backend.admin.service.TaiKhoanService;
import org.example.backend.common.dto.request.TaiKhoanRequest;
import org.example.backend.common.dto.response.TaiKhoanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/tai-khoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping("/nhan-vien/{idNhanVien}")
    public TaiKhoanResponse getTaiKhoanNhanVien(@PathVariable Integer idNhanVien) {
        return taiKhoanService.getTaiKhoanNhanVien(idNhanVien);
    }

    @PostMapping("/nhan-vien/{idNhanVien}")
    public TaiKhoanResponse themTaiKhoanChoNhanVien(@PathVariable Integer idNhanVien,
                                                    @RequestBody TaiKhoanRequest request) {
        return taiKhoanService.themTaiKhoanChoNhanVien(idNhanVien, request);
    }

    @PutMapping("/nhan-vien/{idNhanVien}")
    public TaiKhoanResponse capNhatTaiKhoanNhanVien(@PathVariable Integer idNhanVien,
                                                    @RequestBody TaiKhoanRequest request) {
        return taiKhoanService.capNhatTaiKhoanNhanVien(idNhanVien, request);
    }

    @DeleteMapping("/nhan-vien/{idNhanVien}")
    public String xoaTaiKhoanNhanVien(@PathVariable Integer idNhanVien) {
        taiKhoanService.xoaTaiKhoanNhanVien(idNhanVien);
        return "Xóa tài khoản nhân viên thành công";
    }

    @GetMapping("/khach-hang/{idKhachHang}")
    public TaiKhoanResponse getTaiKhoanKhachHang(@PathVariable Integer idKhachHang) {
        return taiKhoanService.getTaiKhoanKhachHang(idKhachHang);
    }

    @PostMapping("/khach-hang/{idKhachHang}")
    public TaiKhoanResponse themTaiKhoanChoKhachHang(@PathVariable Integer idKhachHang,
                                                     @RequestBody TaiKhoanRequest request) {
        return taiKhoanService.themTaiKhoanChoKhachHang(idKhachHang, request);
    }

    @PutMapping("/khach-hang/{idKhachHang}")
    public TaiKhoanResponse capNhatTaiKhoanKhachHang(@PathVariable Integer idKhachHang,
                                                     @RequestBody TaiKhoanRequest request) {
        return taiKhoanService.capNhatTaiKhoanKhachHang(idKhachHang, request);
    }

    @DeleteMapping("/khach-hang/{idKhachHang}")
    public String xoaTaiKhoanKhachHang(@PathVariable Integer idKhachHang) {
        taiKhoanService.xoaTaiKhoanKhachHang(idKhachHang);
        return "Xóa tài khoản khách hàng thành công";
    }
}