package org.example.backend.admin.controller;

import org.example.backend.admin.service.DanhMucService;
import org.example.backend.common.dto.request.DanhMucRequest;
import org.example.backend.common.dto.response.DanhMucResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/danh-muc")
public class DanhMucConntroller {

    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/hien-thi")
    public List<DanhMucResponse> getAll() {
        return danhMucService.getALL();
    }

    @GetMapping("/chi-tiet/{id}")
    public DanhMucResponse getById(@PathVariable Long id) {
        return danhMucService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody DanhMucRequest request) {
        danhMucService.add(request);
        return "Thêm danh mục thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id, @RequestBody DanhMucRequest request) {
        danhMucService.update(id, request);
        return "Sửa danh mục thành công";
    }

    @PutMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        danhMucService.delete(id);
        return "Xóa danh mục thành công";
    }
}