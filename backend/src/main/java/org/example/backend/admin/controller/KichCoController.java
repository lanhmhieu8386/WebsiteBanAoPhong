package org.example.backend.admin.controller;

import org.example.backend.admin.service.KichCoService;
import org.example.backend.common.dto.request.KichCoRequest;
import org.example.backend.common.dto.response.KichCoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/kich-co")
public class KichCoController {

    @Autowired
    private KichCoService kichCoService;

    @GetMapping("/hien-thi")
    public List<KichCoResponse> getAll() {
        return kichCoService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public KichCoResponse getById(@PathVariable Long id) {
        return kichCoService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody KichCoRequest request) {
        kichCoService.add(request);
        return "Thêm kích cỡ thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id, @RequestBody KichCoRequest request) {
        kichCoService.update(id, request);
        return "Sửa kích cỡ thành công";
    }

    @DeleteMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        kichCoService.delete(id);
        return "Xóa kích cỡ thành công";
    }
}