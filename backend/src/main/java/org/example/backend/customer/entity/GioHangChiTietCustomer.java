package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gio_hang_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GioHangChiTietCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_gio_hang_chi_tiet")
    private String maGioHangChiTiet;

    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHangCustomer gioHang;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTietCustomer sanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;
}