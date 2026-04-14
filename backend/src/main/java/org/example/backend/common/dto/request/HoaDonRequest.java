package org.example.backend.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HoaDonRequest {

    private String maHoaDon;

    private Integer idKhachHang;

    private Integer idNhanVien;

    private String diaChiGiaoHang;

    private String soDienThoai;

    private BigDecimal tongTien;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer trangThai;
}