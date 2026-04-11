package org.example.backend.admin.controller;

import org.example.backend.admin.service.SanPhamChiTietService;
import org.example.backend.common.dto.request.SanPhamChiTietRequest;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @GetMapping("/chi-tiet/san-pham/{id}")
    public List<SanPhamChiTietResponse> getChiTietBySanPhamId(@PathVariable("id") Long id) {
        return sanPhamChiTietService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public SanPhamChiTietResponse getDetailById(@PathVariable("id") Long id) {
        return sanPhamChiTietService.getDetailById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody SanPhamChiTietRequest request) {
        sanPhamChiTietService.add(request);
        return "Thêm sản phẩm chi tiết thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestBody SanPhamChiTietRequest request) {
        sanPhamChiTietService.update(id, request);
        return "Sửa sản phẩm chi tiết thành công";
    }

    @PutMapping("/xoa/{id}")
    public String delete(@PathVariable("id") Long id) {
        sanPhamChiTietService.delete(id);
        return "Xóa sản phẩm chi tiết thành công";
    }
}