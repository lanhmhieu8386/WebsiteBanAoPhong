package org.example.backend.admin.controller;

import org.example.backend.admin.service.HinhAnhService;
import org.example.backend.common.dto.request.HinhAnhRequest;
import org.example.backend.common.dto.response.HinhAnhResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/hinh-anh")
public class HinhAnhController {

    @Autowired
    private HinhAnhService hinhAnhService;

    @GetMapping("/hien-thi")
    public List<HinhAnhResponse> getAll() {
        return hinhAnhService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public HinhAnhResponse getById(@PathVariable Long id) {
        return hinhAnhService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody HinhAnhRequest request) {
        hinhAnhService.add(request);
        return "Thêm hình ảnh thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id,
                         @RequestBody HinhAnhRequest request) {
        hinhAnhService.update(id, request);
        return "Sửa hình ảnh thành công";
    }

    @DeleteMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        hinhAnhService.delete(id);
        return "Xóa hình ảnh thành công";
    }
}