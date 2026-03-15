package org.example.backend.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.backend.common.dto.request.RegisterRequest;
import org.example.backend.common.entity.TaiKhoan;
import org.example.backend.common.repository.TaiKhoanRepository;
import org.example.backend.customer.service.AuthService;
import org.example.backend.secutiry.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TaiKhoanRepository taiKhoanRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req, HttpServletResponse res) {

        String email = req.get("email");
        String password = req.get("password");

        TaiKhoan tk = taiKhoanRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (!tk.getMatKhau().equals(password)) {
            throw new RuntimeException("Sai mật khẩu");
        }

        String role = tk.getVaiTro().getMaVaiTro();

        String token = jwtUtil.generateToken(tk.getEmail(), role);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", role);
        response.put("email", tk.getEmail());
        response.put("name", tk.getTenTaiKhoan());

        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok("Đăng ký thành công");
    }
}