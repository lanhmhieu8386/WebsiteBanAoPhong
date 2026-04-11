package org.example.backend.admin.controller;

import org.example.backend.admin.service.ThuongHieuService;
import org.example.backend.common.dto.request.ThuongHieuRequest;
import org.example.backend.common.dto.response.ThuongHieuResponse;
import org.example.backend.common.entity.ThuongHieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping("/hien-thi")
    public List<ThuongHieuResponse> getAll() {
        return thuongHieuService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public ThuongHieuResponse getById(@PathVariable Long id) {
        return thuongHieuService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody ThuongHieuRequest request) {
        thuongHieuService.add(request);
        return "Thêm thương hiệu thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id, @RequestBody ThuongHieuRequest request) {
        thuongHieuService.update(id, request);
        return "Sửa thương hiệu thành công";
    }

    @PutMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        thuongHieuService.delete(id);
        return "Xóa thương hiệu thành công";
    }
}