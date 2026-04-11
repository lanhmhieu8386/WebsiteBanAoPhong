package org.example.backend.customer.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "san_pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_san_pham", length = 20, unique = true)
    private String maSanPham;

    @Column(name = "ten_san_pham", length = 200)
    private String tenSanPham;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayCapNhat;

    @Column(name = "nguoi_tao", length = 200)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 200)
    private String nguoiCapNhat;

    // ====== Quan hệ ======

    @ManyToOne
    @JoinColumn(name = "id_danh_muc")
    private DanhMucCustomer danhMuc;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieuCustomer thuongHieu;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieuCustomer chatLieu;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY)
    private List<SanPhamChiTietCustomer> sanPhamChiTietList;
}