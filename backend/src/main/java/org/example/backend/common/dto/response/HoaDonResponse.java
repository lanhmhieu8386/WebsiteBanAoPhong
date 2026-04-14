package org.example.backend.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Builder
public class HoaDonResponse {

    private Integer id;

    private String maHoaDon;

    private Integer idKhachHang;
    private String tenKhachHang;

    private Integer idNhanVien;
    private String tenNhanVien;

    private String diaChiGiaoHang;

    private String soDienThoai;

    private BigDecimal tongTien;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer trangThai;

    public HoaDonResponse(
            Integer id,
            String maHoaDon,
            Integer idKhachHang,
            String tenKhachHang,
            Integer idNhanVien,
            String tenNhanVien,
            String diaChiGiaoHang,
            String soDienThoai,
            BigDecimal tongTien,
            LocalDateTime ngayTao,
            LocalDateTime ngayCapNhat,
            String nguoiTao,
            String nguoiCapNhat,
            Integer trangThai
    ) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.idKhachHang = idKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.idNhanVien = idNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.diaChiGiaoHang = diaChiGiaoHang;
        this.soDienThoai = soDienThoai;
        this.tongTien = tongTien;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.nguoiTao = nguoiTao;
        this.nguoiCapNhat = nguoiCapNhat;
        this.trangThai = trangThai;
    }
}