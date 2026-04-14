package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "khach_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @ManyToOne
    @JoinColumn(name = "id_tai_khoan")
    private TaiKhoanCustomer taiKhoanCustomer;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "ngay_sinh")
    private LocalDateTime ngaySinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "trang_thai")
    private Integer trangThai;
}