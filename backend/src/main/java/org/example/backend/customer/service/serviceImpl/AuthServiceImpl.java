package org.example.backend.customer.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.common.dto.request.RegisterRequest;
import org.example.backend.common.entity.KhachHang;
import org.example.backend.common.entity.TaiKhoan;
import org.example.backend.common.entity.VaiTro;
import org.example.backend.common.repository.KhachHangRepository;
import org.example.backend.common.repository.TaiKhoanRepository;
import org.example.backend.common.repository.VaiTroRepository;
import org.example.backend.customer.service.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final KhachHangRepository khachHangRepository;
    private final VaiTroRepository vaiTroRepository;

    @Override
    public void register(RegisterRequest request) {

        // check email tồn tại
        if (taiKhoanRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // lấy role CUSTOMER
        VaiTro role = vaiTroRepository.findByMaVaiTro("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        // tạo tài khoản
        String maTaiKhoan = "TK" + System.currentTimeMillis();

        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(maTaiKhoan);
        tk.setTenTaiKhoan(request.getName());
        tk.setEmail(request.getEmail());
        tk.setMatKhau(request.getPassword());
        tk.setVaiTro(role);

        taiKhoanRepository.save(tk);

        // tạo khách hàng
        String maKhachHang = "KH" + System.currentTimeMillis();

        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(maKhachHang);
        kh.setTenKhachHang(request.getName());
        kh.setEmail(request.getEmail());
        kh.setSoDienThoai(request.getPhone());
        kh.setNgaySinh(request.getNgaySinh().atStartOfDay());
        kh.setTaiKhoan(tk);
        khachHangRepository.save(kh);
    }
}