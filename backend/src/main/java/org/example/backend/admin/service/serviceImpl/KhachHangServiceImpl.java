package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.KhachHangService;
import org.example.backend.common.dto.request.KhachHangRequest;
import org.example.backend.common.dto.response.KhachHangResponse;
import org.example.backend.common.entity.KhachHang;
import org.example.backend.common.entity.TaiKhoan;
import org.example.backend.common.repository.KhachHangRepository;
import org.example.backend.common.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private  KhachHangRepository khachHangRepository;

    @Autowired
    private  TaiKhoanRepository taiKhoanRepository;

    @Override
    public Page<KhachHangResponse> getAll(String keyword, Integer trangThai, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return khachHangRepository.searchKhachHang(
                (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim(),
                trangThai,
                pageable
        );
    }

    @Override
    public KhachHangResponse getById(Integer id) {
        return khachHangRepository.getDetail(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
    }

    @Override
    public KhachHangResponse add(KhachHangRequest request) {
        if (khachHangRepository.existsByMaKhachHang(request.getMaKhachHang(), null)) {
            throw new RuntimeException("Mã khách hàng đã tồn tại");
        }
        if (request.getEmail() != null && khachHangRepository.existsByEmail(request.getEmail(), null)) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (request.getSoDienThoai() != null && khachHangRepository.existsBySoDienThoai(request.getSoDienThoai(), null)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        TaiKhoan taiKhoan = null;
        if (request.getIdTaiKhoan() != null) {
            taiKhoan = taiKhoanRepository.findByIdCustom(request.getIdTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        }

        KhachHang khachHang = KhachHang.builder()
                .maKhachHang(request.getMaKhachHang())
                .taiKhoan(taiKhoan)
                .tenKhachHang(request.getTenKhachHang())
                .ngaySinh(request.getNgaySinh())
                .soDienThoai(request.getSoDienThoai())
                .email(request.getEmail())
                .ngayTao(LocalDateTime.now())
                .nguoiTao(request.getNguoiTao())
                .trangThai(request.getTrangThai() == null ? 1 : request.getTrangThai())
                .build();

        khachHangRepository.save(khachHang);
        return getById(khachHang.getId());
    }

    @Override
    public KhachHangResponse update(Integer id, KhachHangRequest request) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        if (khachHangRepository.existsByMaKhachHang(request.getMaKhachHang(), id)) {
            throw new RuntimeException("Mã khách hàng đã tồn tại");
        }
        if (request.getEmail() != null && khachHangRepository.existsByEmail(request.getEmail(), id)) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (request.getSoDienThoai() != null && khachHangRepository.existsBySoDienThoai(request.getSoDienThoai(), id)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        TaiKhoan taiKhoan = null;
        if (request.getIdTaiKhoan() != null) {
            taiKhoan = taiKhoanRepository.findByIdCustom(request.getIdTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        }

        khachHang.setMaKhachHang(request.getMaKhachHang());
        khachHang.setTaiKhoan(taiKhoan);
        khachHang.setTenKhachHang(request.getTenKhachHang());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setEmail(request.getEmail());
        khachHang.setNgayCapNhat(LocalDateTime.now());
        khachHang.setNguoiCapNhat(request.getNguoiCapNhat());
        khachHang.setTrangThai(request.getTrangThai());

        khachHangRepository.save(khachHang);
        return getById(id);
    }

    @Override
    public void delete(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        khachHangRepository.delete(khachHang);
    }
}