package org.example.backend.admin.controller;

import org.example.backend.admin.service.MauSacService;
import org.example.backend.common.dto.request.MauSacRequest;
import org.example.backend.common.dto.response.MauSacResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/mau-sac")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/hien-thi")
    public List<MauSacResponse> getAll() {
        return mauSacService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public MauSacResponse getById(@PathVariable Long id) {
        return mauSacService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody MauSacRequest request) {
        mauSacService.add(request);
        return "Thêm màu sắc thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id, @RequestBody MauSacRequest request) {
        mauSacService.update(id, request);
        return "Sửa màu sắc thành công";
    }

    @DeleteMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        mauSacService.delete(id);
        return "Xóa màu sắc thành công";
    }
}