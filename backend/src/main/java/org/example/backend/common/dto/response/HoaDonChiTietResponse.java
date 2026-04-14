package org.example.backend.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HoaDonChiTietResponse {

    private Integer id;

    private String maHoaDonChiTiet;

    private Integer idHoaDon;
    private String maHoaDon;

    private Long idSanPhamChiTiet;
    private String maSanPhamChiTiet;
    private String tenSanPham;
    private String tenMauSac;
    private String tenKichCo;
    private String urlAnh;

    private Integer soLuong;

    private BigDecimal gia;

    private BigDecimal thanhTien;

    public HoaDonChiTietResponse(
            Integer id,
            String maHoaDonChiTiet,
            Integer idHoaDon,
            String maHoaDon,
            Long idSanPhamChiTiet,
            String maSanPhamChiTiet,
            String tenSanPham,
            String tenMauSac,
            String tenKichCo,
            String urlAnh,
            Integer soLuong,
            BigDecimal gia
    ) {
        this.id = id;
        this.maHoaDonChiTiet = maHoaDonChiTiet;
        this.idHoaDon = idHoaDon;
        this.maHoaDon = maHoaDon;
        this.idSanPhamChiTiet = idSanPhamChiTiet;
        this.maSanPhamChiTiet = maSanPhamChiTiet;
        this.tenSanPham = tenSanPham;
        this.tenMauSac = tenMauSac;
        this.tenKichCo = tenKichCo;
        this.urlAnh = urlAnh;
        this.soLuong = soLuong;
        this.gia = gia;
        this.thanhTien = (gia != null && soLuong != null)
                ? gia.multiply(BigDecimal.valueOf(soLuong.longValue()))
                : BigDecimal.ZERO;
    }
}