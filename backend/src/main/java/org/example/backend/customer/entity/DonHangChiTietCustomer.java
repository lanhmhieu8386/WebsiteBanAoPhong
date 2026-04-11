package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "don_hang_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class DonHangChiTietCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_don_hang_chi_tiet")
    private String maDonHangChiTiet;

    // FK -> DonHang
    @ManyToOne
    @JoinColumn(name = "id_don_hang")
    private DonHangCustomer donHang;

    // FK -> SanPhamChiTiet
    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTietCustomer sanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia")
    private BigDecimal gia;
    
}
