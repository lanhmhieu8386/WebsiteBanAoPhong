package org.example.backend.customer.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "san_pham_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamChiTietCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_san_pham_chi_tiet", length = 20)
    private String maSanPhamChiTiet;

    // ===== Quan hệ =====

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPhamCustomer sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSacCustomer mauSac;

    @ManyToOne
    @JoinColumn(name = "id_kich_co")
    private KichCoCustomer kichCo;

    // ===== Thuộc tính =====

    @Column(name = "so_luong_ton")
    private Integer soLuongTon = 0;

    @Column(name = "gia", precision = 12, scale = 2)
    private BigDecimal gia;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;
}