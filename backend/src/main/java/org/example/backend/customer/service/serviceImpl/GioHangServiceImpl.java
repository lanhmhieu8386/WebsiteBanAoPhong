package org.example.backend.customer.service.serviceImpl;

import org.example.backend.customer.dto.request.ThemGioHangRequest;
import org.example.backend.customer.dto.response.GioHangResponse;
import org.example.backend.customer.entity.GioHangChiTietCustomer;
import org.example.backend.customer.entity.GioHangCustomer;
import org.example.backend.customer.entity.SanPhamChiTietCustomer;
import org.example.backend.customer.entity.TaiKhoanCustomer;
import org.example.backend.customer.repository.GioHangChiTietRepository;
import org.example.backend.customer.repository.GioHangRepository;
import org.example.backend.customer.repository.SanPhamChiTietCustomerRepository;
import org.example.backend.customer.repository.TaiKhoanCustomerRepository;
import org.example.backend.customer.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private TaiKhoanCustomerRepository taiKhoanCustomerRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private SanPhamChiTietCustomerRepository sanPhamChiTietRepository;

    @Override
    public GioHangResponse themVaoGio(ThemGioHangRequest req) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Long userId = tk.getId();

        // ==========================
        // 1. Tìm giỏ hàng
        // ==========================
        GioHangCustomer gioHang = gioHangRepository
                .findByTaiKhoanCustomer_Id(userId)
                .orElseGet(() -> taoGioHangMoi(userId));

        // ==========================
        // 2. Lấy sản phẩm
        // ==========================
        SanPhamChiTietCustomer spct = sanPhamChiTietRepository
                .findById(req.getIdSanPhamChiTiet())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // ==========================
        // 3. Check tồn tại
        // ==========================
        Optional<GioHangChiTietCustomer> itemOpt =
                gioHangChiTietRepository
                        .findByGioHang_IdAndSanPhamChiTiet_Id(
                                gioHang.getId(),
                                req.getIdSanPhamChiTiet()
                        );

        if (itemOpt.isPresent()) {
            GioHangChiTietCustomer item = itemOpt.get();
            item.setSoLuong(item.getSoLuong() + req.getSoLuong());
            gioHangChiTietRepository.save(item);
        } else {
            GioHangChiTietCustomer newItem = new GioHangChiTietCustomer();
            newItem.setGioHang(gioHang);
            newItem.setSanPhamChiTiet(spct);
            newItem.setSoLuong(req.getSoLuong());
            gioHangChiTietRepository.save(newItem);
        }

        return mapToResponse(gioHang);
    }

    @Override
    public GioHangResponse getMyCart() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        GioHangCustomer gioHang = gioHangRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Chưa có giỏ hàng"));

        return mapToResponse(gioHang);
    }

    @Override
    public GioHangResponse updateSoLuong(ThemGioHangRequest req) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        GioHangCustomer gioHang = gioHangRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Chưa có giỏ hàng"));

        GioHangChiTietCustomer item = gioHangChiTietRepository
                .findByGioHang_IdAndSanPhamChiTiet_Id(
                        gioHang.getId(),
                        req.getIdSanPhamChiTiet()
                )
                .orElseThrow(() -> new RuntimeException("Sản phẩm không có trong giỏ"));

        if (req.getSoLuong() <= 0) {
            gioHangChiTietRepository.delete(item);
        } else {
            item.setSoLuong(req.getSoLuong());
            gioHangChiTietRepository.save(item);
        }

        return mapToResponse(gioHang);
    }

    @Override
    public GioHangResponse removeItem(Long idSanPhamChiTiet) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        TaiKhoanCustomer tk = taiKhoanCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        GioHangCustomer gioHang = gioHangRepository
                .findByTaiKhoanCustomer_Id(tk.getId())
                .orElseThrow(() -> new RuntimeException("Chưa có giỏ hàng"));

        GioHangChiTietCustomer item = gioHangChiTietRepository
                .findByGioHang_IdAndSanPhamChiTiet_Id(
                        gioHang.getId(),
                        idSanPhamChiTiet
                )
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ"));

        gioHangChiTietRepository.delete(item);

        return mapToResponse(gioHang);
    }

    // ==========================
    //  Tạo giỏ hàng mới
    // ==========================
    private GioHangCustomer taoGioHangMoi(Long userId) {
        GioHangCustomer gioHang = new GioHangCustomer();

        TaiKhoanCustomer tk = new TaiKhoanCustomer();
        tk.setId(userId);

        gioHang.setTaiKhoanCustomer(tk);
        gioHang.setMaGioHang("GH_" + System.currentTimeMillis());

        return gioHangRepository.save(gioHang);
    }

    // ==========================
    //  Map sang response
    // ==========================
    private GioHangResponse mapToResponse(GioHangCustomer gioHang) {

        GioHangCustomer gh = gioHangRepository.findById(gioHang.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        // ===== map items =====
        List<GioHangResponse.Item> items = gh.getGioHangChiTiets()
                .stream()
                .map(ct -> {
                    SanPhamChiTietCustomer sp = ct.getSanPhamChiTiet();

                    GioHangResponse.Item item = new GioHangResponse.Item();
                    item.setIdSanPhamChiTiet(sp.getId());
                    item.setTenSanPham(sp.getSanPham().getTenSanPham()); // sửa nếu khác field
                    item.setSoLuong(ct.getSoLuong());
                    item.setGia(sp.getGia());
                    item.setThanhTien(
                            sp.getGia().multiply(BigDecimal.valueOf(ct.getSoLuong()))
                    );

                    return item;
                })
                .toList();

        int tongSoLuong = items.stream()
                .mapToInt(GioHangResponse.Item::getSoLuong)
                .sum();

        BigDecimal tongTien = items.stream()
                .map(GioHangResponse.Item::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return GioHangResponse.builder()
                .idGioHang(gh.getId())
                .maGioHang(gh.getMaGioHang())
                .items(items)
                .tongSoLuong(tongSoLuong)
                .tongTien(tongTien)
                .build();
    }
}
