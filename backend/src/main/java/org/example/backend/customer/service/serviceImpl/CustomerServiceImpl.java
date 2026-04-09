package org.example.backend.customer.service.serviceImpl;

import org.example.backend.common.entity.KhachHang;
import org.example.backend.common.repository.KhachHangRepository;
import org.example.backend.customer.dto.request.CustomerUpdateRequest;
import org.example.backend.customer.dto.response.CustomerResponse;
import org.example.backend.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public CustomerResponse getMyInfo(String email) {
        KhachHang kh = khachHangRepository
                .findByTaiKhoan_Email(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        CustomerResponse res = new CustomerResponse();
        res.setId(kh.getId());
        res.setMaKhachHang(kh.getMaKhachHang());
        res.setTenKhachHang(kh.getTenKhachHang());
        res.setNgaySinh(kh.getNgaySinh());
        res.setSoDienThoai(kh.getSoDienThoai());
        res.setEmail(kh.getEmail());

        return res;
    }

    @Override
    public CustomerResponse updateMyInfo(String email, CustomerUpdateRequest request) {
        KhachHang kh = khachHangRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        kh.setTenKhachHang(request.getTenKhachHang());
        kh.setSoDienThoai(request.getSoDienThoai());
        kh.setEmail(request.getEmail());
        kh.setNgaySinh(request.getNgaySinh());
        kh.setNgayCapNhat(LocalDateTime.now());

        khachHangRepository.save(kh);

        return mapToResponse(kh);
    }

    private CustomerResponse mapToResponse(KhachHang kh) {
        CustomerResponse res = new CustomerResponse();

        res.setId(kh.getId());
        res.setMaKhachHang(kh.getMaKhachHang());
        res.setTenKhachHang(kh.getTenKhachHang());
        res.setNgaySinh(kh.getNgaySinh());
        res.setSoDienThoai(kh.getSoDienThoai());
        res.setEmail(kh.getEmail());

        return res;
    }
}
