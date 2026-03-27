package org.example.backend.admin.controller;

import org.example.backend.admin.service.DanhMucService;
import org.example.backend.common.entity.DanhMuc;
import org.example.backend.common.repository.DanhMucRepository;
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
    public List<DanhMuc> getAll() {
        return danhMucService.getALL();
    }

    @GetMapping("/chi-tiet/{id}")
    public DanhMuc getById(@PathVariable Long id) {
        return danhMucService.getById(id);
    }
}
