package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Table (name = "san_pham_chi_tiet")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SanPhamChiTiet {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_san_pham_chi_tiet")
    private String maSanPhamChiTiet;

    @Column (name = "so_luong_ton")
    private Long soLuongTon;

    @Column (name = "gia")
    private BigDecimal gia;

    @Column (name = "ngay_tao")
    private Date ngayTao;

    @Column (name = "trang_thai")
    private Boolean trangThai;


    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_kich_co")
    private KichCo kichCo;

    @ManyToOne
    @JoinColumn(name = "id_hinh_anh")
    private HinhAnh hinhAnh;
}
