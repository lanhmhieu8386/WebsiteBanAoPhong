package org.example.backend.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangResponse {

    private Long idGioHang;
    private String maGioHang;

    private List<Item> items;

    private Integer tongSoLuong;
    private BigDecimal tongTien;

    @Data
    public static class Item {
        private Long idSanPhamChiTiet;
        private String tenSanPham;
        private Integer soLuong;
        private BigDecimal gia;
        private BigDecimal thanhTien;
        private String hinhAnh;
    }
}