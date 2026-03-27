package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table (name = "danh_muc")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DanhMuc {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_danh_muc")
    private String maDanhMuc;

    @Column (name = "ten_danh_muc")
    private String tenDanhMuc;

    @Column (name = "mo_ta")
    private String moTa;

    @Column (name = "trang_thai")
    private Boolean trangThai;

    @Column (name = "ngay_tao")
    private Date ngayTao;

    @Column (name = "ngay_cap_nhat")
    private Date ngayCapNhat;


}
