package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.entity.KhachHang;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "don_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_don_hang", unique = true)
    private String maDonHang;

    // FK -> KhachHang
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    // FK -> TrangThaiDonHang
    @ManyToOne
    @JoinColumn(name = "id_trang_thai")
    private TrangThaiDonHang trangThai;

    @Column(name = "ngay_dat")
    private Date ngayDat;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "dia_chi_giao_hang")
    private String diaChiGiaoHang;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    private Date ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

}
