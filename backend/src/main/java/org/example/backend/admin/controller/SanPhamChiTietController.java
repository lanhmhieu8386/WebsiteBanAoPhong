package org.example.backend.admin.controller;

import org.example.backend.admin.service.SanPhamChiTietService;
import org.example.backend.common.dto.response.SanPhamChiTietResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/san-pham-chi-tiet")
public class SanPhamChiTietController {
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;



    @GetMapping("/chi-tiet/{id}")
    public List<SanPhamChiTietResponse> getChiTietBySanPhamId(@PathVariable("id") Long id){
        return sanPhamChiTietService.getById(id);
    }

}
