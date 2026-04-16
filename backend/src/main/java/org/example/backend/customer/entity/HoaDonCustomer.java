package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_hoa_don", unique = true)
    private String maHoaDon;

    // FK -> KhachHang
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHangCustomer khachHang;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;
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

    @OneToMany(mappedBy = "hoaDon")
    private List<HoaDonChiTietCustomer> hoaDonChiTiets;
}
