package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.NhanVienService;
import org.example.backend.common.dto.request.NhanVienRequest;
import org.example.backend.common.dto.response.NhanVienResponse;
import org.example.backend.common.entity.NhanVien;
import org.example.backend.common.entity.TaiKhoan;
import org.example.backend.common.repository.NhanVienRepository;
import org.example.backend.common.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public Page<NhanVienResponse> getAll(String keyword, Boolean trangThai, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return nhanVienRepository.searchNhanVien(
                (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim(),
                trangThai,
                pageable
        );
    }

    @Override
    public NhanVienResponse getById(Integer id) {
        return nhanVienRepository.getDetail(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    @Override
    public NhanVienResponse add(NhanVienRequest request) {
        if (nhanVienRepository.existsByMaNhanVien(request.getMaNhanVien(), null)) {
            throw new RuntimeException("Mã nhân viên đã tồn tại");
        }
        if (request.getEmail() != null && nhanVienRepository.existsByEmail(request.getEmail(), null)) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (request.getSoDienThoai() != null && nhanVienRepository.existsBySoDienThoai(request.getSoDienThoai(), null)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        TaiKhoan taiKhoan = null;
        if (request.getIdTaiKhoan() != null) {
            taiKhoan = taiKhoanRepository.findByIdCustom(request.getIdTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        }

        NhanVien nhanVien = NhanVien.builder()
                .maNhanVien(request.getMaNhanVien())
                .taiKhoan(taiKhoan)
                .tenNhanVien(request.getTenNhanVien())
                .soDienThoai(request.getSoDienThoai())
                .diaChi(request.getDiaChi())
                .ngaySinh(request.getNgaySinh())
                .gioiTinh(request.getGioiTinh())
                .email(request.getEmail())
                .ngayTao(LocalDateTime.now())
                .nguoiTao(request.getNguoiTao())
                .trangThai(request.getTrangThai() == null ? true : request.getTrangThai())
                .build();

        nhanVienRepository.save(nhanVien);
        return getById(nhanVien.getId());
    }

    @Override
    public NhanVienResponse update(Integer id, NhanVienRequest request) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        if (nhanVienRepository.existsByMaNhanVien(request.getMaNhanVien(), id)) {
            throw new RuntimeException("Mã nhân viên đã tồn tại");
        }
        if (request.getEmail() != null && nhanVienRepository.existsByEmail(request.getEmail(), id)) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (request.getSoDienThoai() != null && nhanVienRepository.existsBySoDienThoai(request.getSoDienThoai(), id)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        TaiKhoan taiKhoan = null;
        if (request.getIdTaiKhoan() != null) {
            taiKhoan = taiKhoanRepository.findByIdCustom(request.getIdTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        }

        nhanVien.setMaNhanVien(request.getMaNhanVien());
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVien.setTenNhanVien(request.getTenNhanVien());
        nhanVien.setSoDienThoai(request.getSoDienThoai());
        nhanVien.setDiaChi(request.getDiaChi());
        nhanVien.setNgaySinh(request.getNgaySinh());
        nhanVien.setGioiTinh(request.getGioiTinh());
        nhanVien.setEmail(request.getEmail());
        nhanVien.setNgayCapNhat(LocalDateTime.now());
        nhanVien.setNguoiCapNhat(request.getNguoiCapNhat());
        nhanVien.setTrangThai(request.getTrangThai());

        nhanVienRepository.save(nhanVien);
        return getById(id);
    }

    @Override
    public void delete(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        nhanVienRepository.delete(nhanVien);
    }
}
