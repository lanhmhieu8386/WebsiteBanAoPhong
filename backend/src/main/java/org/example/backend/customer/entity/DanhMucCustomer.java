package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "danh_muc")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DanhMucCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_danh_muc")
    private String maDanhMuc;

    @Column(name = "ten_danh_muc")
    private String tenDanhMuc;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;
}
