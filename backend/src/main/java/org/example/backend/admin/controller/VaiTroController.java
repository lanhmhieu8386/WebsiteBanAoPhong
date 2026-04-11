package org.example.backend.admin.controller;

import org.example.backend.admin.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
@RequestMapping("/api/admin/vai-tro")
public class VaiTroController {
    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping("/hien-thi")
    public Object getAll() {
        return vaiTroService.getAll();
    }
}
