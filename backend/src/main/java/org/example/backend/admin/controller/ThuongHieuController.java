package org.example.backend.admin.controller;

import org.example.backend.admin.service.ThuongHieuService;
import org.example.backend.common.entity.DanhMuc;
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
    public List<ThuongHieu> getAll() {
        return thuongHieuService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public ThuongHieu getById(@PathVariable Long id) {
        return thuongHieuService.getById(id);
    }
}
