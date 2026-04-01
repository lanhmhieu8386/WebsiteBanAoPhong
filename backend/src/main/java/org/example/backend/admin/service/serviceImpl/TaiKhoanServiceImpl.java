package org.example.backend.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.backend.admin.service.TaiKhoanService;
import org.example.backend.common.dto.request.TaiKhoanRequest;
import org.example.backend.common.dto.response.TaiKhoanResponse;
import org.example.backend.common.entity.KhachHang;
import org.example.backend.common.entity.NhanVien;
import org.example.backend.common.entity.TaiKhoan;
import org.example.backend.common.entity.VaiTro;
import org.example.backend.common.repository.KhachHangRepository;
import org.example.backend.common.repository.NhanVienRepository;
import org.example.backend.common.repository.TaiKhoanRepository;
import org.example.backend.common.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public TaiKhoanResponse getTaiKhoanNhanVien(Integer idNhanVien) {
        return nhanVienRepository.getTaiKhoanByNhanVienId(idNhanVien)
                .orElse(null);
    }

    @Override
    public TaiKhoanResponse themTaiKhoanChoNhanVien(Integer idNhanVien, TaiKhoanRequest request) {
        NhanVien nhanVien = nhanVienRepository.findByIdCustom(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        if (nhanVien.getTaiKhoan() != null) {
            throw new RuntimeException("Nhân viên này đã có tài khoản");
        }

        TaiKhoan taiKhoan = taoTaiKhoan(request);
        taiKhoanRepository.save(taiKhoan);

        nhanVien.setTaiKhoan(taiKhoan);
        nhanVienRepository.save(nhanVien);

        return taiKhoanRepository.getDetail(taiKhoan.getId())
                .orElseThrow(() -> new RuntimeException("Không lấy được tài khoản vừa tạo"));
    }

    @Override
    public TaiKhoanResponse capNhatTaiKhoanNhanVien(Integer idNhanVien, TaiKhoanRequest request) {
        NhanVien nhanVien = nhanVienRepository.findByIdCustom(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        if (nhanVien.getTaiKhoan() == null) {
            throw new RuntimeException("Nhân viên này chưa có tài khoản");
        }

        TaiKhoan taiKhoan = capNhatTaiKhoan(nhanVien.getTaiKhoan(), request);
        taiKhoanRepository.save(taiKhoan);

        return taiKhoanRepository.getDetail(taiKhoan.getId())
                .orElseThrow(() -> new RuntimeException("Không lấy được tài khoản"));
    }

    @Override
    public void xoaTaiKhoanNhanVien(Integer idNhanVien) {
        NhanVien nhanVien = nhanVienRepository.findByIdCustom(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        if (nhanVien.getTaiKhoan() == null) {
            throw new RuntimeException("Nhân viên này chưa có tài khoản");
        }

        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        nhanVien.setTaiKhoan(null);
        nhanVienRepository.save(nhanVien);

        taiKhoanRepository.delete(taiKhoan);
    }

    @Override
    public TaiKhoanResponse getTaiKhoanKhachHang(Integer idKhachHang) {
        return khachHangRepository.getTaiKhoanByKhachHangId(idKhachHang)
                .orElse(null);
    }

    @Override
    public TaiKhoanResponse themTaiKhoanChoKhachHang(Integer idKhachHang, TaiKhoanRequest request) {
        KhachHang khachHang = khachHangRepository.findByIdCustom(idKhachHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        if (khachHang.getTaiKhoan() != null) {
            throw new RuntimeException("Khách hàng này đã có tài khoản");
        }

        TaiKhoan taiKhoan = taoTaiKhoan(request);
        taiKhoanRepository.save(taiKhoan);

        khachHang.setTaiKhoan(taiKhoan);
        khachHangRepository.save(khachHang);

        return taiKhoanRepository.getDetail(taiKhoan.getId())
                .orElseThrow(() -> new RuntimeException("Không lấy được tài khoản vừa tạo"));
    }

    @Override
    public TaiKhoanResponse capNhatTaiKhoanKhachHang(Integer idKhachHang, TaiKhoanRequest request) {
        KhachHang khachHang = khachHangRepository.findByIdCustom(idKhachHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        if (khachHang.getTaiKhoan() == null) {
            throw new RuntimeException("Khách hàng này chưa có tài khoản");
        }

        TaiKhoan taiKhoan = capNhatTaiKhoan(khachHang.getTaiKhoan(), request);
        taiKhoanRepository.save(taiKhoan);

        return taiKhoanRepository.getDetail(taiKhoan.getId())
                .orElseThrow(() -> new RuntimeException("Không lấy được tài khoản"));
    }

    @Override
    public void xoaTaiKhoanKhachHang(Integer idKhachHang) {
        KhachHang khachHang = khachHangRepository.findByIdCustom(idKhachHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        if (khachHang.getTaiKhoan() == null) {
            throw new RuntimeException("Khách hàng này chưa có tài khoản");
        }

        TaiKhoan taiKhoan = khachHang.getTaiKhoan();
        khachHang.setTaiKhoan(null);
        khachHangRepository.save(khachHang);

        taiKhoanRepository.delete(taiKhoan);
    }

    private TaiKhoan taoTaiKhoan(TaiKhoanRequest request) {
        validateTrung(request, null);

        VaiTro vaiTro = null;
        if (request.getIdVaiTro() != null) {
            vaiTro = vaiTroRepository.findById(request.getIdVaiTro())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
        }

        return TaiKhoan.builder()
                .maTaiKhoan(request.getMaTaiKhoan())
                .tenTaiKhoan(request.getTenTaiKhoan())
                .email(request.getEmail())
                .matKhau(request.getMatKhau())
                .soDienThoai(request.getSoDienThoai())
                .diaChi(request.getDiaChi())
                .vaiTro(vaiTro)
                .ngayTao(LocalDateTime.now())
                .build();
    }

    private TaiKhoan capNhatTaiKhoan(TaiKhoan taiKhoan, TaiKhoanRequest request) {
        validateTrung(request, taiKhoan.getId());

        VaiTro vaiTro = null;
        if (request.getIdVaiTro() != null) {
            vaiTro = vaiTroRepository.findById(request.getIdVaiTro())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
        }

        taiKhoan.setMaTaiKhoan(request.getMaTaiKhoan());
        taiKhoan.setTenTaiKhoan(request.getTenTaiKhoan());
        taiKhoan.setEmail(request.getEmail());
        taiKhoan.setMatKhau(request.getMatKhau());
        taiKhoan.setSoDienThoai(request.getSoDienThoai());
        taiKhoan.setDiaChi(request.getDiaChi());
        taiKhoan.setVaiTro(vaiTro);

        return taiKhoan;
    }

    private void validateTrung(TaiKhoanRequest request, Integer id) {
        if (taiKhoanRepository.existsByMaTaiKhoan(request.getMaTaiKhoan(), id)) {
            throw new RuntimeException("Mã tài khoản đã tồn tại");
        }
        if (request.getEmail() != null && taiKhoanRepository.existsByEmail(request.getEmail(), id)) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (request.getSoDienThoai() != null && taiKhoanRepository.existsBySoDienThoai(request.getSoDienThoai(), id)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }
    }
}