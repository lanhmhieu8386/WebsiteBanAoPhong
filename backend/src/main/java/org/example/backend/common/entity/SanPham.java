package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table (name = "san_pham")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SanPham {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_san_pham")
    private String maSanPham;

    @Column (name = "ten_san_pham")
    private String tenSanPham;

    @Column (name = "mo_ta")
    private String moTa;

    @Column (name = "ngay_tao")
    private Date ngayTao;

    @Column (name = "ngay_cap_nhat")
    private Date ngayCapNhat;

    @Column (name = "nguoi_tao")
    private String nguoiTao;

    @Column (name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column (name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "id_danh_muc")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chatLieu;

}
