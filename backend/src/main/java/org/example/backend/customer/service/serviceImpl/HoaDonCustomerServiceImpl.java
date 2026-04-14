package org.example.backend.customer.service.serviceImpl;

import jakarta.transaction.Transactional;
import org.example.backend.customer.dto.request.HoaDonCustomerRequest;
import org.example.backend.customer.dto.response.HoaDonCustomerResponse;
import org.example.backend.customer.entity.*;
import org.example.backend.customer.enums.TrangThaiHoaDon;
import org.example.backend.customer.mapper.HoaDonMapper;
import org.example.backend.customer.repository.*;
import org.example.backend.customer.service.HoaDonCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class HoaDonCustomerServiceImpl implements HoaDonCustomerService {

    @Autowired
    private HoaDonCustomerRepository hoaDonCustomerRepository;
    @Autowired
    private TaiKhoanCustomerRepository taiKhoanCustomerRepository;
    @Autowired
    private SanPhamChiTietCustomerRepository sanPhamChiTietCustomerRepository;
    @Autowired
    private HoaDonChiTietCustomerRepository hoaDonChiTietCustomerRepository;
    @Autowired
    private HoaDonMapper hoaDonMapper;
    @Autowired
    private KhachHangCustomerRepository khachHangCustomerRepository;
    @Autowired
    private GioHangRepository gioHangRepository;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;


    @Override
    @Transactional
    public HoaDonCustomerResponse checkout(HoaDonCustomerRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        KhachHangCustomer khachHang = khachHangCustomerRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        GioHangCustomer gioHang = gioHangRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        if (gioHang.getGioHangChiTiets() == null || gioHang.getGioHangChiTiets().isEmpty()) {
            throw new RuntimeException("Giỏ hàng không có sản phẩm");
        }


        HoaDonCustomer hoaDon = hoaDonMapper.mapToEntity(
                request,
                khachHang,
                email
        );

        hoaDon.setTrangThai(TrangThaiHoaDon.CHO_XAC_NHAN.getValue());
        hoaDon = hoaDonCustomerRepository.save(hoaDon);

        // ===== 5. copy từ giỏ hàng sang hóa đơn =====
        BigDecimal tongTien = BigDecimal.ZERO;

        for (GioHangChiTietCustomer item : gioHang.getGioHangChiTiets()) {

            SanPhamChiTietCustomer sp = item.getSanPhamChiTiet();

            BigDecimal gia = sp.getGia();
            BigDecimal thanhTien =
                    gia.multiply(BigDecimal.valueOf(item.getSoLuong()));

            tongTien = tongTien.add(thanhTien);

            HoaDonChiTietCustomer ct = new HoaDonChiTietCustomer();
            ct.setHoaDon(hoaDon);
            ct.setSanPhamChiTiet(sp);
            ct.setSoLuong(item.getSoLuong());
            ct.setGia(gia);

            hoaDonChiTietCustomerRepository.save(ct);
        }

        hoaDon.setTongTien(tongTien);
        hoaDonCustomerRepository.save(hoaDon);

        gioHangChiTietRepository.deleteByGioHangId(gioHang.getId());

        return hoaDonMapper.mapToResponse(hoaDon);
    }

    public List<HoaDonCustomerResponse> getMyOrders() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        KhachHangCustomer khachHang = khachHangCustomerRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        List<HoaDonCustomer> list = hoaDonCustomerRepository
                .findByKhachHang_Id(khachHang.getId());

        return list.stream()
                .map(hoaDonMapper::mapToResponse)
                .toList();
    }

    public HoaDonCustomerResponse getOrderDetail(Long orderId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow();

        KhachHangCustomer khachHang = khachHangCustomerRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow();

        HoaDonCustomer hoaDon = hoaDonCustomerRepository
                .findByIdAndKhachHang_Id(orderId, khachHang.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn"));

        return hoaDonMapper.mapToResponse(hoaDon);
    }

    public HoaDonCustomerResponse cancelOrder(Long orderId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        KhachHangCustomer khachHang = khachHangCustomerRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        HoaDonCustomer hoaDon = hoaDonCustomerRepository
                .findByIdAndKhachHang_Id(orderId, khachHang.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn"));

        if (hoaDon.getTrangThai() != 1) {
            throw new RuntimeException("Không thể hủy đơn này");
        }
        
        hoaDon.setTrangThai(0);
        hoaDon.setNgayCapNhat(new Date());

        hoaDonCustomerRepository.save(hoaDon);

        return hoaDonMapper.mapToResponse(hoaDon);
    }
}
