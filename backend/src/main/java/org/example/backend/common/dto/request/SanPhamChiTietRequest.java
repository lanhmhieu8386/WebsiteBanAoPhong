package org.example.backend.common.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SanPhamChiTietRequest {

    private String maSanPhamChiTiet;

    private Long soLuongTon;

    private BigDecimal gia;

    private Boolean trangThai;

    private Long idSanPham;

    private Long idMauSac;

    private Long idKichCo;
}