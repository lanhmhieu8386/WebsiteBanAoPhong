package org.example.backend.admin.controller;

import org.example.backend.admin.service.SanPhamService;
import org.example.backend.common.dto.request.SanPhamRequest;
import org.example.backend.common.dto.response.SanPhamDetailResponse;
import org.example.backend.common.dto.response.SanPhamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/hien-thi")
    public List<SanPhamResponse> getAll() {
        return sanPhamService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public SanPhamDetailResponse getById(@PathVariable Long id) {
        return sanPhamService.getById(id);
    }

    @PostMapping("/them")
    public void add(@RequestBody SanPhamRequest request) {
        sanPhamService.add(request);
    }

    @PutMapping("/sua/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody SanPhamRequest request) {
        sanPhamService.update(id, request);
    }

    @DeleteMapping("/xoa/{id}")
    public void delete(@PathVariable Long id) {
        sanPhamService.delete(id);
    }

}