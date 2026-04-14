package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "hoa_don_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class HoaDonChiTietCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_hoa_don_chi_tiet")
    private String maHoaDonChiTiet;

    // FK -> DonHang
    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDonCustomer hoaDon;

    // FK -> SanPhamChiTiet
    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTietCustomer sanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia")
    private BigDecimal gia;

}
