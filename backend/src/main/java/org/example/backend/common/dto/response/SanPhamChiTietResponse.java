package org.example.backend.common.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SanPhamChiTietResponse {

    private Long id;
    private String urlAnh;
    private String tenSanPham;
    private String tenDanhMuc;

    // ===== LIST =====
    private BigDecimal giaMin;
    private Long tongSoLuong;

    // ===== DETAIL =====
    private String tenMauSac;
    private String tenKichCo;
    private BigDecimal gia;
    private Long soLuong;

    private Boolean trangThai;

    // ===== Constructor cho LIST =====
    public SanPhamChiTietResponse(
            Long id,
            String urlAnh,
            String tenSanPham,
            String tenDanhMuc,
            BigDecimal giaMin,
            Long tongSoLuong,
            Boolean trangThai
    ) {
        this.id = id;
        this.urlAnh = urlAnh;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.giaMin = giaMin;
        this.tongSoLuong = tongSoLuong;
        this.trangThai = trangThai;
    }

    // ===== Constructor cho DETAIL =====
    public SanPhamChiTietResponse(
            Long id,
            String urlAnh,
            String tenSanPham,
            String tenDanhMuc,
            String mauSac,
            String kichCo,
            BigDecimal gia,
            Long soLuong,
            Boolean trangThai
    ) {
        this.id = id;
        this.urlAnh = urlAnh;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.tenMauSac = mauSac;
        this.tenKichCo = kichCo;
        this.gia = gia;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }
}