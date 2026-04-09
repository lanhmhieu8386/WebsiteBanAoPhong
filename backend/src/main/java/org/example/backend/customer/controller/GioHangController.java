package org.example.backend.customer.controller;

import org.example.backend.customer.dto.request.ThemGioHangRequest;
import org.example.backend.customer.dto.response.GioHangResponse;
import org.example.backend.customer.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gio-hang")
@CrossOrigin(originPatterns = "http://localhost:*")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    @PostMapping("/them")
    public ResponseEntity<GioHangResponse> themVaoGio(
            @RequestBody ThemGioHangRequest request
    ) {
        return ResponseEntity.ok(
                gioHangService.themVaoGio(request)
        );
    }
}
