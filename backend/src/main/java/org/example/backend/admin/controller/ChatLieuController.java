package org.example.backend.admin.controller;

import org.example.backend.admin.service.ChatLieuService;
import org.example.backend.admin.service.DanhMucService;
import org.example.backend.common.entity.ChatLieu;
import org.example.backend.common.entity.DanhMuc;
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
    public List<ChatLieu> getAll() {
        return chatLieuService.getAll();
    }

    @GetMapping("/chi-tiet/{id}")
    public ChatLieu getById(@PathVariable Long id) {
        return chatLieuService.getById(id);
    }
}
