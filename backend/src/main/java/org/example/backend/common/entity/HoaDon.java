package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "hoa_don")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_hoa_don", length = 20)
    private String maHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @Column(name = "dia_chi_giao_hang", columnDefinition = "NVARCHAR(255)")
    private String diaChiGiaoHang;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "tong_tien", precision = 12, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "ngay_tao", insertable = false, updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "nguoi_tao", columnDefinition = "NVARCHAR(200)")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", columnDefinition = "NVARCHAR(200)")
    private String nguoiCapNhat;

    @Column(name = "trang_thai")
    private Integer trangThai;
}