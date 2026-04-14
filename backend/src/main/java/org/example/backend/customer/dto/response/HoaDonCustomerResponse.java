package org.example.backend.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonCustomerResponse {

    private Long id;
    private String maHoaDon;

    private String tenKhachHang;
    private Long idKhachHang;

    private Integer trangThai;

    private Date ngayDat;
    private BigDecimal tongTien;

    private String diaChiGiaoHang;

    private List<Item> items;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private Long idSanPhamChiTiet;
        private String tenSanPham;
        private String hinhAnh;
        private Integer soLuong;
        private BigDecimal gia;
        private BigDecimal thanhTien;
    }
}