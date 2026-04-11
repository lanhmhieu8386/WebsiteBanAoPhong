package org.example.backend.admin.controller;

import org.example.backend.admin.service.ChatLieuService;
import org.example.backend.common.dto.request.ChatLieuRequest;
import org.example.backend.common.dto.response.ChatLieuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/chat-lieu")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("/hien-thi")
    public List<ChatLieuResponse> getAll() {
        return chatLieuService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public ChatLieuResponse getById(@PathVariable Long id) {
        return chatLieuService.getById(id);
    }

    @PostMapping("/them")
    public String add(@RequestBody ChatLieuRequest request) {
        chatLieuService.add(request);
        return "Thêm chất liệu thành công";
    }

    @PutMapping("/sua/{id}")
    public String update(@PathVariable Long id, @RequestBody ChatLieuRequest request) {
        chatLieuService.update(id, request);
        return "Sửa chất liệu thành công";
    }

    @PutMapping("/xoa/{id}")
    public String delete(@PathVariable Long id) {
        chatLieuService.delete(id);
        return "Xóa chất liệu thành công";
    }
}